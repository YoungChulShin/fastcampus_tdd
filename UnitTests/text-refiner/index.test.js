const faker = require("faker");
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
    ${"hello     world"} | ${"hello world"}
    ${"hello      world"} | ${"hello world"}
    ${"hello       world"} | ${"hello world"}
`('sut transforms "$source" to "$expected"', ({source, expected}) => {
    const actual = sut(source);
    expect(actual).toBe(expected);
});

test.each`
    source | expected
    ${"hello\t world"} | ${"hello world"}
    ${"hello \tworld"} | ${"hello world"}
`('sut transforms "$source" that contains tab character to "$expected"', 
({source, expected}) => {
    const actual = sut(source)
    expect(actual).toBe(expected);
});

test.each`
    source | bannedwords | expected
    ${"hello mockist"} | ${["mockist", "purist"]} | ${"hello *******"}
    ${"hello purist"} | ${["mockist", "purist"]} | ${"hello ******"}
`('sut transfroms "$source" to "$expected"', 
({ source, bannedwords, expected}) => {
    const actual = sut(source, { bannedwords });
    expect(actual).toBe(expected);
});

// random parameter test
describe('given banned word', () => {
    const bannedword = faker.lorem.word();
    const source = "hello " + bannedword;
    const expected = "hello " + "*".repeat(bannedword.length);

    test('${bannedword} when invoke sut then it returns ${expected}', () => {
        const actual = sut(source, { bannedwords: [bannedword]});
        expect(actual).toBe(expected);
    })
})