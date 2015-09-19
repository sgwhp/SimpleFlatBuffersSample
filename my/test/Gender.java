// automatically generated, do not modify

package my.test;

public final class Gender {
  private Gender() { }
  public static final byte Male = 1;
  public static final byte Female = 2;

  private static final String[] names = { "Male", "Female", };

  public static String name(int e) { return names[e - Male]; }
};

