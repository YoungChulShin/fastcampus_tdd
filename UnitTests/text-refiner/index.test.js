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

// for 문을 이용한 테스트
// test('sut correctyly works', () => {
//     for (const source of ['hello  world', 'hello   world', 'hello    world']) {
//         const actual = sut(source);
//         expect(actual).toBe("hello world");
//     }
// });

// parameterized 테스트
test.each`
    source              | expected
    ${"hello  world"}   | ${"hello world"}
    ${"hello   world"}  | ${"hello world"}
    ${"hello    world"} | ${"hello world"}
`('sut transforms "$source" to "$expected"', ({source, expected}) => {
    const actual = sut(source);
    expect(actual).toBe(expected);
})