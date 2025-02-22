module.exports = function(grunt) {
    grunt.loadNpmTasks('grunt-screeps');
    let config = require('./.screeps.json');
    grunt.initConfig({
        screeps: {
            options: {
                email: config.email,
                password: config.password,
                branch: 'default',
                //server: 'season'
            },
            dist: {
                src: ['target/scala-2.13/scala-example-bot-fastopt/main.js']
            }
        }
    });
}