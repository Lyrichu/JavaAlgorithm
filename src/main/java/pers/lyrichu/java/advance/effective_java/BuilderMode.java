package pers.lyrichu.java.advance.effective_java;


/*
 *@ClassName BuilderMode
 *@Description Effective Java Builder 构建器的实现
 *@Author lyrichu
 *@Date 18-11-29
 *@Version 1.0
 */

public class BuilderMode {

    private final int id;
    private final String name;
    private final int age;
    private final boolean sex;
    private final boolean married;

    public static class Builder {
        private final int id; // required
        private final String name; // required

        private int age = 0;
        private boolean sex = true; // true for male,false for female
        private boolean married = false;

        // 构造器
        public Builder(int id,String name) {
            this.id = id;
            this.name = name;
        }

        // 一系列setter
        public Builder age(int val) {
            age = val;
            return this;
        }

        public Builder sex(boolean val) {
            sex = val;
            return this;
        }

        public Builder married(boolean val) {
            married = val;
            return this;
        }

        // build 方法
        public BuilderMode build() {
            return new BuilderMode(this);
        }
    }

    private BuilderMode(Builder builder) {
        id = builder.id;
        name = builder.name;
        age = builder.age;
        sex = builder.sex;
        married = builder.married;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isSex() {
        return sex;
    }

    public boolean isMarried() {
        return married;
    }
}
