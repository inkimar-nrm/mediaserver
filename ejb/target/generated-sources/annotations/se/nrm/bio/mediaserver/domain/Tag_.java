package se.nrm.bio.mediaserver.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.nrm.bio.mediaserver.domain.Media;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-02-21T01:12:00")
@StaticMetamodel(Tag.class)
public class Tag_ { 

    public static volatile SingularAttribute<Tag, Integer> id;
    public static volatile SingularAttribute<Tag, String> tagKey;
    public static volatile SingularAttribute<Tag, String> tagValue;
    public static volatile SingularAttribute<Tag, Media> media;

}