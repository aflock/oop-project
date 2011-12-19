#pragma once

#include <iostream>
#include <sstream>

#include "include/java_lang.h"

using namespace java::lang;

namespace xtc {
  namespace oop {
    struct __Test;
    struct __Test_VT;

    typedef __rt::Ptr<__Test> Test;

    struct __Rest;
    struct __Rest_VT;

    typedef __rt::Ptr<__Rest> Rest;

    struct __Test {
      __Test_VT* __vptr;
      static const Object __super;
      static const Object $R1;
      static const Object $R2;
      static const Object $R3;
      static const Object $R4;
      int32_t $count;

      __Test();

      static Test Test$void(Test __this = __rt::null());

      static void __delete(__Test*);

      static Object m1$void(Test);
      static Object m2$void();
      static Test m3$void(Test);
      static Test m4$void(Test);
      static Test m5$Test(Test, Test);
      static Object m6$Test(Test, Test);
      static Object m6$Rest(Test, Rest);
      static Object m7$Object(Test, Object);
      static Object m7$String(Test, String);
      static Object m7$Test(Test, Test);
      static Object m7$Rest(Test, Rest);
      static Object m8$Test(Test, Test);
      static Object m8$Rest(Test, Rest);
      static Object m8$Test$Test(Test, Test, Test);
      static Object m8$Rest$Test(Test, Rest, Test);
      static Object m9$int16_t(Test, int16_t);
      static Object m9$int32_t(Test, int32_t);
      static Object m9$int64_t(Test, int64_t);
      static Object m10$int32_t(Test, int32_t);
      static Object m10$int64_t(Test, int64_t);
      static void main$array1_String(__rt::Ptr<__rt::Array<String > >);

      static Class __class();

      static __Test_VT __vtable;
    };

    struct __Test_VT {
      Class __isa;
      void (*__delete)(__Test*);
      int32_t (*hashCode$void)(Test);
      bool (*equals$Object)(Test, Object);
      Class (*getClass$void)(Test);
      String (*toString$void)(Test);
      Object (*m1$void)(Test);
      Test (*m3$void)(Test);
      Test (*m4$void)(Test);
      Test (*m5$Test)(Test, Test);
      Object (*m6$Test)(Test, Test);
      Object (*m6$Rest)(Test, Rest);
      Object (*m7$Object)(Test, Object);
      Object (*m7$String)(Test, String);
      Object (*m7$Test)(Test, Test);
      Object (*m7$Rest)(Test, Rest);
      Object (*m8$Test)(Test, Test);
      Object (*m8$Rest)(Test, Rest);
      Object (*m8$Test$Test)(Test, Test, Test);
      Object (*m8$Rest$Test)(Test, Rest, Test);
      Object (*m9$int16_t)(Test, int16_t);
      Object (*m9$int32_t)(Test, int32_t);
      Object (*m9$int64_t)(Test, int64_t);
      Object (*m10$int32_t)(Test, int32_t);
      Object (*m10$int64_t)(Test, int64_t);

      __Test_VT()
      : __isa(__Test::__class()),
      __delete((void(*)(__Test*))&__Object::__delete),
      hashCode$void((int32_t(*)(Test))&__Object::hashCode$void),
      equals$Object((bool(*)(Test,Object))&__Object::equals$Object),
      getClass$void((Class(*)(Test))&__Object::getClass$void),
      toString$void((String(*)(Test))&__Object::toString$void),
      m1$void(&__Test::m1$void),
      m3$void(&__Test::m3$void),
      m4$void(&__Test::m4$void),
      m5$Test(&__Test::m5$Test),
      m6$Test(&__Test::m6$Test),
      m6$Rest(&__Test::m6$Rest),
      m7$Object(&__Test::m7$Object),
      m7$String(&__Test::m7$String),
      m7$Test(&__Test::m7$Test),
      m7$Rest(&__Test::m7$Rest),
      m8$Test(&__Test::m8$Test),
      m8$Rest(&__Test::m8$Rest),
      m8$Test$Test(&__Test::m8$Test$Test),
      m8$Rest$Test(&__Test::m8$Rest$Test),
      m9$int16_t(&__Test::m9$int16_t),
      m9$int32_t(&__Test::m9$int32_t),
      m9$int64_t(&__Test::m9$int64_t),
      m10$int32_t(&__Test::m10$int32_t),
      m10$int64_t(&__Test::m10$int64_t) {}
    };

    struct __Rest {
      __Rest_VT* __vptr;
      static const xtc::oop::Test __super;
      int32_t $count;
      int32_t $round;

      __Rest();

      static Rest Rest$void(Rest __this = __rt::null());

      static void __delete(__Rest*);

      static Object m1$void(Rest);
      static Object m2$void();
      static Test m4$void(Rest);
      static Object m7$Test(Rest, Test);
      static Object m9$int16_t(Rest, int16_t);
      static Object m9$int32_t(Rest, int32_t);
      static Object m9$int64_t(Rest, int64_t);
      static Object m10$int32_t(Rest, int32_t);
      static Object m10$int64_t(Rest, int64_t);
      static int32_t hashCode$void(Rest);
      static void main$array1_String(__rt::Ptr<__rt::Array<String > >);

      static Class __class();

      static __Rest_VT __vtable;
    };

    struct __Rest_VT {
      Class __isa;
      void (*__delete)(__Rest*);
      int32_t (*hashCode$void)(Rest);
      bool (*equals$Object)(Rest, Object);
      Class (*getClass$void)(Rest);
      String (*toString$void)(Rest);
      Object (*m1$void)(Rest);
      Test (*m3$void)(Rest);
      Test (*m4$void)(Rest);
      Test (*m5$Test)(Rest, Test);
      Object (*m6$Test)(Rest, Test);
      Object (*m6$Rest)(Rest, Rest);
      Object (*m7$Object)(Rest, Object);
      Object (*m7$String)(Rest, String);
      Object (*m7$Test)(Rest, Test);
      Object (*m7$Rest)(Rest, Rest);
      Object (*m8$Test)(Rest, Test);
      Object (*m8$Rest)(Rest, Rest);
      Object (*m8$Test$Test)(Rest, Test, Test);
      Object (*m8$Rest$Test)(Rest, Rest, Test);
      Object (*m9$int16_t)(Rest, int16_t);
      Object (*m9$int32_t)(Rest, int32_t);
      Object (*m9$int64_t)(Rest, int64_t);
      Object (*m10$int32_t)(Rest, int32_t);
      Object (*m10$int64_t)(Rest, int64_t);

      __Rest_VT()
      : __isa(__Rest::__class()),
      __delete((void(*)(__Rest*))&__Object::__delete),
      hashCode$void(&__Rest::hashCode$void),
      equals$Object((bool(*)(Rest,Object))&__Object::equals$Object),
      getClass$void((Class(*)(Rest))&__Object::getClass$void),
      toString$void((String(*)(Rest))&__Object::toString$void),
      m1$void(&__Rest::m1$void),
      m3$void((Test(*)(Rest))&__Test::m3$void),
      m4$void(&__Rest::m4$void),
      m5$Test((Test(*)(Rest,Test))&__Test::m5$Test),
      m6$Test((Object(*)(Rest,Test))&__Test::m6$Test),
      m6$Rest((Object(*)(Rest,Rest))&__Test::m6$Rest),
      m7$Object((Object(*)(Rest,Object))&__Test::m7$Object),
      m7$String((Object(*)(Rest,String))&__Test::m7$String),
      m7$Test(&__Rest::m7$Test),
      m7$Rest((Object(*)(Rest,Rest))&__Test::m7$Rest),
      m8$Test((Object(*)(Rest,Test))&__Test::m8$Test),
      m8$Rest((Object(*)(Rest,Rest))&__Test::m8$Rest),
      m8$Test$Test((Object(*)(Rest,Test,Test))&__Test::m8$Test$Test),
      m8$Rest$Test((Object(*)(Rest,Rest,Test))&__Test::m8$Rest$Test),
      m9$int16_t(&__Rest::m9$int16_t),
      m9$int32_t(&__Rest::m9$int32_t),
      m9$int64_t(&__Rest::m9$int64_t),
      m10$int32_t(&__Rest::m10$int32_t),
      m10$int64_t(&__Rest::m10$int64_t) {}
    };

  }
}
