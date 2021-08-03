function refineText(source, options) {
    return [normalizeWhiteSpaces, compactWhiteSpaces, maskBannedWords].reduce(
        (value, filter) => filter(value, options), 
        source
    );
}

function maskBannedWords(source, options) {
    if (options) {
        for (const bannedword of options.bannedwords) {
            source = source.replace(bannedword, "*".repeat(bannedword.length));
        }
    }
    return source;
}

function normalizeWhiteSpaces(source) {
    return source.replace("\t", " ");
}

function compactWhiteSpaces(source) {
    return source.indexOf("  ") < 0 
    ? source
    : compactWhiteSpaces(source.replace("  ", " "));
}

module.exports = refineText;