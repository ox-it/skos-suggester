import os
from datetime import datetime

from fabric.api import *
from fabric.contrib import *
from fabric.contrib.files import exists
from fabric import utils

FAST_TOPICS_URL = "http://experimental.worldcat.org/fast/download/FASTTopical.nt.zip"


"""
Environments
"""

ENVIRONMENTS = ('dev', 'staging')

@task
def local():
    env.hosts = ['talks.vm']
    env.user = 'talks'
    env.skos_home = '/srv/talks'
    env.current_skos = '/srv/talks/current_fast_topics.nt'
    env.tdb_store = '/srv/talks/tdb_store'
    env.skos_jar = '/srv/talks/skos-suggester-1.0-SNAPSHOT.jar'
    env.skos_jar_conf_file = '/srv/talks/skos-suggester-configuration.yaml'
    env.tdbloader2_path = '/srv/jena/apache-jena-2.12.1/bin/tdbloader2'


@task
def staging():
    env.hosts = ['talks-dev.oucs.ox.ac.uk']
    env.user = 'talks'
    env.skos_home = '/srv/talks'
    env.current_skos = '/srv/talks/current_fast_topics.nt'
    env.tdb_store = '/srv/talks/tdb_store'
    env.skos_jar = '/srv/talks/skos-suggester-1.0-SNAPSHOT.jar'
    env.skos_jar_conf_file = '/srv/talks/skos-suggester-configuration.yaml'
    env.tdbloader2_path = '/srv/jena/apache-jena-2.12.1/bin/tdbloader2'


"""
Methods
"""

@task
def download_fast_topics():
    """Download the FAST topics and unzip it
    """
    fast_file = '%s-fast-topics.nt' % (datetime.now().strftime('%Y%m%d%H%M'))
    extracted_file = 'FASTTopical.nt'
    run('curl -L --progress-bar "{fast_download}" > {home}/{file}.zip'.format(fast_download=FAST_TOPICS_URL,
                                                                                home=env.skos_home,
                                                                                file=fast_file))
    run('unzip {home}/{file}.zip {extracted} -d {home}'.format(home=env.skos_home,
                                                                file=fast_file,
                                                                extracted=extracted_file))
    run('rm -f {home}/{file}.zip'.format(home=env.skos_home, file=fast_file))
    run('mv {home}/{extracted} {home}/{file}'.format(home=env.skos_home, file=fast_file, extracted=extracted_file))
    run('rm -f {path}'.format(path=env.current_skos))
    run('ln -s {home}/{file} {current}'.format(home=env.skos_home, file=fast_file, current=env.current_skos))

@task
def load_into_tdb():
    """Load current SKOS file into TDB
    """
    # first, make sure it is a new store
    run('rm -rf {tdb_store}'.format(tdb_store=env.tdb_store))
    run('{tdbloader2} -loc {tdb_store} {skos_file}'.format(tdbloader2=env.tdbloader2_path,
                                                            tdb_store=env.tdb_store,
                                                            skos_file=env.current_skos))

@task
def run_importer():
    """Run the importer into Apache Solr
    """
    run('java -jar {skos_jar} tdbimport -d {tdb_store} {skos_jar_conf}'.format(skos_jar=env.skos_jar,
                                                                                tdb_store=env.tdb_store,
                                                                                skos_jar_conf=env.skos_jar_conf_file))

@task
def update_topics():
    """Download latest file, load it and run importers
    """
    download_fast_topics()
    load_into_tdb()
    run_importer()
