package stc.test.socialmedia.base.model;

import stc.test.socialmedia.base.model.HasId;

public interface HasIdAndEmail extends HasId {
    String getEmail();
}