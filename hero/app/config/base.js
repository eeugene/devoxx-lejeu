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
      modules: [path.join(__dirname, '/../src'), 'node_modules']
    },
    module: {
      loaders: [{
        test: /\.(ts|tsx)$/,
        loaders: ['ts-loader'],
        exclude: [/\.(spec|e2e)\.(ts|tsx)$/]
      },
      {
        test: /\.(jpg|png|gif)$/,
        loader: 'file-loader'
      }, {
        test: /\.(woff|woff2|eot|ttf|svg)$/,
        loader: 'url-loader?limit=100000'
      },
      {
        test: /\.css$/,
        loaders: ['style-loader', 'css-loader']
      },
      {
        test: /(component[\/\\]).*\.less$/i, 
        use: ['style-loader',
              {loader: 'css-loader', options: {imortLoaders: 1}},
              'less-loader',
              ]
      },
      ],
    },
    plugins: [
      new HtmlWebpackPlugin({
        template: 'src/index.html',
      })
    ],
  };
}