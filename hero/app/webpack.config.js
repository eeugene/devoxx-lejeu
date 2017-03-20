
function buildConfig(env = 'dev') {
  return require('./config/' + env + '.js')({ env })
}

module.exports = buildConfig;
