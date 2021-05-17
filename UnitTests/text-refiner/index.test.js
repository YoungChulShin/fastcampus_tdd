const sut = require("./index");

test('sut transforms "hello  world" to "hello world"', () => {
    const actual = sut("hello  world");
    expect(actual).toBe("hello world");
});

