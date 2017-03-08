module.exports = wallaby => ({
    files: [
        'src/**/*.ts',
        'src/**/*.fixture.ts',
        'spec/**/*.ts',
        '!src/**/*.spec.ts'
    ],
    tests: [
        'src/**/*.spec.ts',
        'spec/**/*.spec.ts'
    ],
    env: {
        type: 'node'
    },
    testFramework: 'jest',
    debug: true
});