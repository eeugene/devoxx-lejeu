var path = require('path')

var jsonServer = require('json-server')
var server = jsonServer.create()
var router = jsonServer.router(path.join(__dirname, 'db.json'))
var middlewares = jsonServer.defaults()

server.use(middlewares)

server.use(jsonServer.bodyParser)
server.use(function (req, res, next) {
  if (req.method === 'POST') {
    // Converts POST to GET and move payload to query params
    // This way it will make JSON Server that it's GET request
    req.method = 'GET'
    req.query = req.body
  }
  // Continue to JSON Server router
  next()
})

server.get('/api/login', function (req, res, next) {
  let credentials =JSON.parse(Object.keys(req.query)[0])
  if (credentials.username == '')
  {
    res.sendStatus(401)
  } else {
    next()
  }
})

server.use('/api',router)

server.listen(3000, function () {
  console.log('JSON Server is running')
})