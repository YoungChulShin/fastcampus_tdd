function refineText(s) {
    return s.replace("  ", " ").replace(" ", "");
}

module.export = refineText;