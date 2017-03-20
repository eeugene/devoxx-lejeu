const webpackMerge = require('webpack-merge');

const commonConfig = require('./base.js');

module.exports = function (env) {
  return webpackMerge(commonConfig(), {
    devtool: 'cheap-module-source-map',
    devServer: {
      inline: true,
      proxy: {
        "/api": {
          target: "http://localhost:3000"
        }
      },
      historyApiFallback: true
    }
  });
};
