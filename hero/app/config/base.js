let path = require('path');
let HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = function () {
  return {
    entry: {
      'main': './src/main.tsx',
    },
    output: {
      path: path.join(__dirname, '/../dist'),
      filename: '[name].bundle.js',
      // publicPath: publicPath,
      sourceMapFilename: '[name].map'
    },
    resolve: {
      extensions: ['.tsx', '.ts', '.js', '.json'],
      modules: [path.join(__dirname, 'src'), 'node_modules']
    },
    module: {
      loaders: [{
        test: /\.(ts|tsx)$/,
        loaders: ['ts-loader'],
        exclude: [/\.(spec|e2e)\.(ts|tsx)$/]
      }, {
        test: /\.css$/,
        loaders: ['to-string-loader', 'css-loader']
      },
      {
        test: /(component[\/\\]).*\.less$/i, 
        loaders: ['style-loader','css-loader','less-loader']
      },
      {
        test: /\.(jpg|png|gif)$/,
        loader: 'file-loader'
      }, {
        test: /\.(woff|woff2|eot|ttf|svg)$/,
        loader: 'url-loader?limit=100000'
      }],
    },
    plugins: [
      new HtmlWebpackPlugin({
        template: 'src/index.html',
      })
    ],
  };
}