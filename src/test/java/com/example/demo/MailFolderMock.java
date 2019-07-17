package com.example.demo;

import org.jvnet.mock_javamail.Mailbox;
import org.jvnet.mock_javamail.MockFolder;
import org.jvnet.mock_javamail.MockStore;

public class MailFolderMock extends MockFolder {

    private String name;

    public MailFolderMock(MockStore store, Mailbox mailbox, String name) {
        super(store, mailbox);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return name;
    }

}