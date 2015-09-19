// automatically generated, do not modify

package my.test;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class Klass extends Table {
  public static Klass getRootAsKlass(ByteBuffer _bb) { return getRootAsKlass(_bb, new Klass()); }
  public static Klass getRootAsKlass(ByteBuffer _bb, Klass obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public Klass __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public Person students(int j) { return students(new Person(), j); }
  public Person students(Person obj, int j) { int o = __offset(6); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int studentsLength() { int o = __offset(6); return o != 0 ? __vector_len(o) : 0; }
  public Person teacher() { return teacher(new Person()); }
  public Person teacher(Person obj) { int o = __offset(8); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }

  public static int createKlass(FlatBufferBuilder builder,
      int name,
      int students,
      int teacher) {
    builder.startObject(3);
    Klass.addTeacher(builder, teacher);
    Klass.addStudents(builder, students);
    Klass.addName(builder, name);
    return Klass.endKlass(builder);
  }

  public static void startKlass(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addStudents(FlatBufferBuilder builder, int studentsOffset) { builder.addOffset(1, studentsOffset, 0); }
  public static int createStudentsVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startStudentsVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static void addTeacher(FlatBufferBuilder builder, int teacherOffset) { builder.addOffset(2, teacherOffset, 0); }
  public static int endKlass(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishKlassBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
};

