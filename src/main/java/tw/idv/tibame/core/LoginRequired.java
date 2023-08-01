package tw.idv.tibame.core;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

@Target({ TYPE, METHOD })
public @interface LoginRequired {

}
