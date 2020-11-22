// config.devServer.watchOptions = {
//     poll: 2000
// };
//

config.module.rules.push({
    test: /\.(woff|woff2|eot|ttf|otf)$/,
    use: [
        'file-loader',
    ],
});