var express = require('express');
var path = require('path');
var httpProxy = require('http-proxy');
var proxy = httpProxy.createProxyServer();
var proxyStats = httpProxy.createProxyServer({
        ignorePath:true
});
var app = express();

app.use('/',  express.static('../dist/'));
app.all('/api/*', function (req, res) {
    //console.log('in /api/*');
    proxy.web(req, res, {
        target: 'http://localhost:8080'
    });
});
app.all('/leaderboard', function (req, res) {
    console.log('in /leaderboard');
    proxy.web(req, res, {
        target: 'http://localhost:8081'
    });
});
app.all('/statistics', function (req, res) {
    console.log('in /statistics');
    proxyStats.web(req, res, {
        target: 'http://localhost:8083'
    });
});
proxy.on('error', function(e) {
  console.log('Could not connect to proxy, please try again...');
});
app.listen(80);
