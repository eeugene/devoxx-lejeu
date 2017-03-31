var express = require('express');
var path = require('path');
var httpProxy = require('http-proxy');
var proxy = httpProxy.createProxyServer();
var app = express();

app.use('/',  express.static('../dist/'));
app.all('/*', function (req, res) {
    proxy.web(req, res, {
        target: 'http://localhost:8080'
    });
  });
proxy.on('error', function(e) {
  console.log('Could not connect to proxy, please try again...');
});
app.listen(80);