module.exports = function(grunt) {
    grunt.loadNpmTasks('grunt-screeps');
    let config = require('./.screeps.json');
    grunt.initConfig({
        screeps: {
            options: {
                email: config.user.email,
                password: config.user.password,
                token: config.user.token,
                branch: 'default',
                server: {
                    host: config.server.host,
                    port: config.server.port,
                    http: config.server.http
                },
            },
            dist: {
                src: ['target/scala-2.13/scala-example-bot-fastopt/main.js']
            }
        }
    });
}