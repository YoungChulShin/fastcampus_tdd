function refineText(s, options) {
    s = s
    .replace("     ", " ")
    .replace("\t", " ")
    .replace("  ", " ")
    .replace("  ", " ")
    .replace("  ", " ")
    .replace("mockist", "*******")
    .replace("purist", "******");

    if (options) {
        for (const bannedword of options.bannedwords) {
            s = s.replace(bannedword, "*".repeat(bannedword.length));
        }
    }

    return s;
}

module.exports = refineText;