from locust import HttpLocust, TaskSet, task

from random import choice

class WebServiceTasks(TaskSet):

    URIS = ['http://id.worldcat.org/fast/1749402', 'http://id.worldcat.org/fast/851994',
    'http://id.worldcat.org/fast/1749403', 'http://id.worldcat.org/fast/851995',
    'http://id.worldcat.org/fast/1749404', 'http://id.worldcat.org/fast/851996',
    'http://id.worldcat.org/fast/1749405', 'http://id.worldcat.org/fast/851897',
    'http://id.worldcat.org/fast/1749406', 'http://id.worldcat.org/fast/851998']
    SUGGEST = ['foo', 'comp', 'med', 'm', 'ch', 'a', 'b', 'pomp', 'boo']
    SEARCH = ['foot', 'football', 'computer', 'series', 'ontology', 'movie', 'film']

    @task
    def get(self):
        self.client.get("/get?uri="+choice(self.URIS), name='/get?uri=[uri]')

    @task
    def suggest(self):
        self.client.get("/suggest?q="+choice(self.SUGGEST), name='/suggest?q=[query]')

    @task
    def search(self):
        self.client.get("/search?q="+choice(self.SEARCH), name='/search?q=[query]')


class WebServiceUser(HttpLocust):
    task_set = WebServiceTasks
    min_wait = 5000
    max_wait = 15000
