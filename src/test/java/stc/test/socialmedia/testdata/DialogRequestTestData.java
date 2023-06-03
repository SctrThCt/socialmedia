package stc.test.socialmedia.testdata;

import stc.test.socialmedia.messaging.model.DialogRequest;
import stc.test.socialmedia.messaging.model.DialogRequestStatus;

import static stc.test.socialmedia.testdata.UserTestData.admin;
import static stc.test.socialmedia.testdata.UserTestData.third;

public class DialogRequestTestData {

    public static DialogRequest request1 = new DialogRequest(1,third,admin);
    public static DialogRequest accepted = new DialogRequest(1,third,admin, DialogRequestStatus.ACCEPTED);
}
