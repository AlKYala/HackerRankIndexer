package de.yalama.hackerrankindexer.shared.HashingAlgorithms;

public enum HashingAlgorithm {

    MD5("MD5"),
    SHA256("SHA-256");

    private String value;

    private HashingAlgorithm(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
