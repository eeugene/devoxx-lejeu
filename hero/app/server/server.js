var express = require('express');
var path = require('path');
var httpProxy = require('http-proxy');
var proxy = httpProxy.createProxyServer();
var proxyNoPath = httpProxy.createProxyServer({
        ignorePath:true
});
var app = express();

app.use('/',  express.static('../dist/'));

function routeToHeroApi(req,res) {
   proxy.web(req, res, {
        target: 'http://localhost:8080'
    });
}

function routeToLeaderboard(req,res) {
   proxy.web(req, res, {
        target: 'http://localhost:8081'
    });
}

app.all('/login', routeToHeroApi);
app.all('/register', routeToHeroApi);
app.all('/api/*', routeToHeroApi);

app.all('/leaderboard/', routeToLeaderboard);
app.all('/leaderboard/*', routeToLeaderboard);

app.all('/statistics', function (req, res) {
    proxyNoPath.web(req, res, {
        target: 'http://localhost:8083'
    });
});
proxy.on('error', function(e) {
  console.log('Could not connect to proxy, please try again...');
});
app.listen(80);
