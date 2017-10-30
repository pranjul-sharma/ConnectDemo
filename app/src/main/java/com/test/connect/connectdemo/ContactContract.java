package com.test.connect.connectdemo;

import android.provider.BaseColumns;

class ContactContract {

    static class ContactEntry implements BaseColumns {

        static final String TABLE_NAME="contacts";
        static final String COL_CONTACT_NAME = "name";
        static final String COL_CONTACT_EMAIl = "email";
        static final String COL_CONTACT_PHONE = "phone";
    }

    static class GroupEntry implements BaseColumns{
        static final String TABLE_NAME = "groups_info_name";
        static final String COL_GROUP_NAME = "group_name";
    }

    static class GroupDetailEntry implements BaseColumns{
        static final String TABLE_NAME = "group_member";
        static final String COL_GROUP_ID = "group_id";
    }
}
