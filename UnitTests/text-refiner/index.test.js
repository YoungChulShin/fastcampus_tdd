const sut = require("./index");

// test('sut transforms "hello  world" to "hello world"', () => {
//     const actual = sut("hello  world");
//     expect(actual).toBe("hello world");
// });

// test('sut transforms "hello   world" to "hello world"', () => {
//     const actual = sut("hello   world");
//     expect(actual).toBe("hello world");
// });

// test('sut transforms "hello    world" to "hello world"', () => {
//     const actual = sut("hello    world");
//     expect(actual).toBe("hello world");
// });

test('sut correctyly works', () => {
    for (const source of ['hello  world', 'hello   world', 'hello    world']) {
        const actual = sut(source);
        expect(actual).toBe("hello world");
    }
});