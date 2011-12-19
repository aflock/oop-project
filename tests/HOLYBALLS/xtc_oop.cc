#include "xtc_oop.h"

namespace __rt {
  template<>
  Array<int16_t>::Array(const int32_t length)
  : __vptr(&__vtable), length(length), __data(new int16_t[length]) {
    std::memset(__data, 0, length * sizeof(int16_t));
  }

  template<>
  java::lang::Class Array<int16_t >::__class() {
    static java::lang::Class k = 
      new java::lang::__Class(literal("[S"),
        Array<java::lang::Object >::__class(),
        java::lang::__Short::TYPE());
    return k;
  }

  template<>
  java::lang::Class Array<java::lang::String >::__class() {
    static java::lang::Class k = 
      new java::lang::__Class(literal("[Ljava.lang.String;"),
        Array<java::lang::Object >::__class(),
        java::lang::__String::__class());
    return k;
  }

}

namespace xtc {
  namespace oop {
    const Object __Test::__super = new __Object();

    const Object xtc::oop::__Test::$R1 = __Object::Object$void();

    const Object xtc::oop::__Test::$R2 = __Object::Object$void();

    const Object xtc::oop::__Test::$R3 = __Object::Object$void();

    const Object xtc::oop::__Test::$R4 = __Object::Object$void();

    __Test::__Test()
    : __vptr(&__vtable) {}

    Test __Test::Test$void(Test __this) {
      if (__rt::null() == __this)
        __this = new __Test();
      __this->$count = 0;
      __this->$count = 0;
      return __this;
    }

    void __Test::__delete(__Test* __this) {
      delete __this;
    }

    Object __Test::m1$void(Test __this) {
      return xtc::oop::__Test::$R1;
    }

    Object __Test::m2$void() {
      return xtc::oop::__Test::$R3;
    }

    Test __Test::m3$void(Test __this) {
      __this->$count++;
      return __this;
    }

    Test __Test::m4$void(Test __this) {
      __this->$count++;
      return __this;
    }

    Test __Test::m5$Test(Test __this, Test $t) {
      __rt::checkNotNull($t);
      return ({
        Test c = $t->__vptr->m3$void($t);
        __rt::checkNotNull(c);
        c->__vptr->m4$void(c);
      });
    }

    Object __Test::m6$Test(Test __this, Test $t) {
      return xtc::oop::__Test::$R1;
    }

    Object __Test::m6$Rest(Test __this, Rest $r) {
      return xtc::oop::__Test::$R2;
    }

    Object __Test::m7$Object(Test __this, Object $o) {
      return xtc::oop::__Test::$R3;
    }

    Object __Test::m7$String(Test __this, String $s) {
      return xtc::oop::__Test::$R4;
    }

    Object __Test::m7$Test(Test __this, Test $t) {
      return xtc::oop::__Test::$R1;
    }

    Object __Test::m7$Rest(Test __this, Rest $r) {
      return xtc::oop::__Test::$R2;
    }

    Object __Test::m8$Test(Test __this, Test $t) {
      return xtc::oop::__Test::$R1;
    }

    Object __Test::m8$Rest(Test __this, Rest $r) {
      return xtc::oop::__Test::$R2;
    }

    Object __Test::m8$Test$Test(Test __this, Test $t1, Test $t2) {
      return xtc::oop::__Test::$R3;
    }

    Object __Test::m8$Rest$Test(Test __this, Rest $r, Test $t) {
      return xtc::oop::__Test::$R4;
    }

    Object __Test::m9$int16_t(Test __this, int16_t $n1) {
      return __rt::null();
    }

    Object __Test::m9$int32_t(Test __this, int32_t $n1) {
      return __rt::null();
    }

    Object __Test::m9$int64_t(Test __this, int64_t $n1) {
      return __rt::null();
    }

    Object __Test::m10$int32_t(Test __this, int32_t $n1) {
      return __rt::null();
    }

    Object __Test::m10$int64_t(Test __this, int64_t $n1) {
      return __rt::null();
    }

    void __Test::main$array1_String(__rt::Ptr<__rt::Array<String > > $args) {
      int16_t $n = 1;
      Test $t;
      Rest $r;
      Object $o = __rt::null();
      int32_t $test = 0;
      int32_t $success = 0;
      std::cout << __rt::literal("PASS Test.main()") << std::endl;
      $success++;
      $test++;
      if ((((xtc::oop::__Test::$R1 != __rt::null() && xtc::oop::__Test::$R1 != xtc::oop::__Test::$R2) && xtc::oop::__Test::$R1 != xtc::oop::__Test::$R3) && xtc::oop::__Test::$R1 != xtc::oop::__Test::$R4)) {
        std::cout << __rt::literal("PASS Object.<init>()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL Object.<init>()") << std::endl;
      }
      $test++;
      $r = xtc::oop::__Rest::Rest$void();
      __rt::checkNotNull($r);
      $o = $r->__vptr->m1$void($r);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS r.m1()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m1()") << std::endl;
      }
      $test++;
      $t = $r;
      if ($t == $r) {
        std::cout << __rt::literal("PASS t == r") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t == r") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      if ($t->__vptr->equals$Object($t, $r)) {
        std::cout << __rt::literal("PASS t.equals(r)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.equals(r)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      if ($r->__vptr->equals$Object($r, $t)) {
        std::cout << __rt::literal("PASS r.equals(t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.equals(t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      int32_t $h = $r->__vptr->hashCode$void($r);
      if (7353 == $h) {
        std::cout << __rt::literal("PASS 7353 == r.hashCode()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL 7353 == r.hashCode()") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      String $s1 = $t->__vptr->toString$void($t);
      __rt::checkNotNull($r);
      String $s2 = $r->__vptr->toString$void($r);
      __rt::checkNotNull($s1);
      if ($s1->__vptr->equals$Object($s1, $s2)) {
        std::cout << __rt::literal("PASS t.toString().equals(r.toString())") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.toString().equals(r.toString())") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m1$void($t);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS t.m1()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m1()") << std::endl;
      }
      $test++;
      $o = xtc::oop::__Rest::m2$void();
      if (xtc::oop::__Test::$R4 == $o) {
        std::cout << __rt::literal("PASS Rest.m2()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL Rest.m2()") << std::endl;
      }
      $test++;
      $o = xtc::oop::__Rest::m2$void();
      if (xtc::oop::__Test::$R4 == $o) {
        std::cout << __rt::literal("PASS r.m2()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m2()") << std::endl;
      }
      $test++;
      Test $tr = $r;
      $o = xtc::oop::__Test::m2$void();
      if (xtc::oop::__Test::$R3 == $o) {
        std::cout << __rt::literal("PASS tr.m2()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL tr.m2()") << std::endl;
      }
      $test++;
      $o = xtc::oop::__Test::m2$void();
      if (xtc::oop::__Test::$R3 == $o) {
        std::cout << __rt::literal("PASS Test.m2()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL Test.m2()") << std::endl;
      }
      $test++;
      $o = xtc::oop::__Test::m2$void();
      if (xtc::oop::__Test::$R3 == $o) {
        std::cout << __rt::literal("PASS t.m2()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m2()") << std::endl;
      }
      $test++;
      $t = xtc::oop::__Test::Test$void();
      if ($t != $r) {
        std::cout << __rt::literal("PASS t != r") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t != r") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      if ((!$t->__vptr->equals$Object($t, $r))) {
        std::cout << __rt::literal("PASS ! t.equals(r)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL ! t.equals(r)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $s1 = $t->__vptr->toString$void($t);
      __rt::checkNotNull($s1);
      if ((!$s1->__vptr->equals$Object($s1, $s2))) {
        std::cout << __rt::literal("PASS ! t.toString().equals(r.toString())") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL ! t.toString().equals(r.toString())") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m1$void($t);
      if (xtc::oop::__Test::$R1 == $o) {
        std::cout << __rt::literal("PASS t.m1()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m1()") << std::endl;
      }
      $test++;
      $o = $t;
      if (({
        Class k = __Test::__class();
        k->__vptr->isInstance$Object(k, $o);
      })) {
        std::cout << __rt::literal("PASS o instanceof Test") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL o instanceof Test") << std::endl;
      }
      $test++;
      if (({
        Class k = __Object::__class();
        k->__vptr->isInstance$Object(k, $o);
      })) {
        std::cout << __rt::literal("PASS o instanceof Object") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL o instanceof Object") << std::endl;
      }
      $test++;
      if ((!({
        Class k = __String::__class();
        k->__vptr->isInstance$Object(k, $o);
      }))) {
        std::cout << __rt::literal("PASS ! (o instanceof String)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL ! (o instanceof String)") << std::endl;
      }
      $test++;
      $o = __rt::java_cast<Test>($o)->__vptr->m1$void(__rt::java_cast<Test>($o));
      if (xtc::oop::__Test::$R1 == $o) {
        std::cout << __rt::literal("PASS (Test)o") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL (Test)o") << std::endl;
      }
      $test++;
      $o = xtc::oop::__Test::m2$void();
      if (xtc::oop::__Test::$R3 == $o) {
        std::cout << __rt::literal("PASS t.m2()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m2()") << std::endl;
      }
      $test++;
      if (0 == $t->$count) {
        std::cout << __rt::literal("PASS Test.<init>()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL Test.<init>()") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      if ((0 == $r->$round && 0 == $r->$count)) {
        std::cout << __rt::literal("PASS Rest.<init>()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL Rest.<init>()") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      ({
        Test c = $t->__vptr->m3$void($t);
        __rt::checkNotNull(c);
        c->__vptr->m4$void(c);
      });
      if (2 == $t->$count) {
        std::cout << __rt::literal("PASS t.m3().m4()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m3().m4()") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      ({
        Test c = $r->__vptr->m3$void($r);
        __rt::checkNotNull(c);
        c->__vptr->m4$void(c);
      });
      __rt::checkNotNull($r);
      if ((1 == $r->$round && 1 == $r->$count)) {
        std::cout << __rt::literal("PASS r.m3().m4()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m3().m4()") << std::endl;
      }
      $test++;
      $t->$count = 0;
      __rt::checkNotNull($t);
      ({
        Test c = ({
          Test c = $t->__vptr->m5$Test($t, $t);
          __rt::checkNotNull(c);
          c->__vptr->m3$void(c);
        });
        __rt::checkNotNull(c);
        c->__vptr->m4$void(c);
      });
      if (4 == $t->$count) {
        std::cout << __rt::literal("PASS t.m5(t).m3().m4()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m5(t).m3().m4()") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $r->$count = 0;
      __rt::checkNotNull($r);
      $r->$round = 0;
      __rt::checkNotNull($r);
      ({
        Test c = ({
          Test c = $r->__vptr->m5$Test($r, $r);
          __rt::checkNotNull(c);
          c->__vptr->m3$void(c);
        });
        __rt::checkNotNull(c);
        c->__vptr->m4$void(c);
      });
      __rt::checkNotNull($r);
      if ((2 == $r->$round && 2 == $r->$count)) {
        std::cout << __rt::literal("PASS r.m5(r).m3().m4()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m5(r).m3().m4()") << std::endl;
      }
      $test++;
      $t->$count = 0;
      __rt::checkNotNull($r);
      $r->$count = 0;
      __rt::checkNotNull($r);
      $r->$round = 0;
      __rt::checkNotNull($r);
      ({
        Test c = ({
          Test c = $r->__vptr->m5$Test($r, $t);
          __rt::checkNotNull(c);
          c->__vptr->m3$void(c);
        });
        __rt::checkNotNull(c);
        c->__vptr->m4$void(c);
      });
      __rt::checkNotNull($r);
      if (((0 == $r->$round && 0 == $r->$count) && 4 == $t->$count)) {
        std::cout << __rt::literal("PASS r.m5(t).m3().m4()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m5(t).m3().m4()") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m6$Test($t, $t);
      if (xtc::oop::__Test::$R1 == $o) {
        std::cout << __rt::literal("PASS t.m6(t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m6(t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m6$Rest($t, $r);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS t.m6(r)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m6(r)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m6$Test($r, $t);
      if (xtc::oop::__Test::$R1 == $o) {
        std::cout << __rt::literal("PASS r.m6(t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m6(t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m6$Rest($r, $r);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS r.m6(r)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m6(r)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m7$Test($t, $t);
      if (xtc::oop::__Test::$R1 == $o) {
        std::cout << __rt::literal("PASS t.m7(t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m7(t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m7$Rest($t, $r);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS t.m7(r)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m7(r)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m7$Object($t, $o);
      if (xtc::oop::__Test::$R3 == $o) {
        std::cout << __rt::literal("PASS t.m7(o)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m7(o)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m7$String($t, $s1);
      if (xtc::oop::__Test::$R4 == $o) {
        std::cout << __rt::literal("PASS t.m7(s1)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m7(s1)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m7$Test($r, $t);
      if (xtc::oop::__Test::$R3 == $o) {
        std::cout << __rt::literal("PASS r.m7(t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m7(t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m7$Rest($r, $r);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS r.m7(r)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m7(r)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m8$Test($t, $t);
      if (xtc::oop::__Test::$R1 == $o) {
        std::cout << __rt::literal("PASS t.m8(t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m8(t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      $o = $t->__vptr->m8$Rest($t, $r);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS t.m8(r)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL t.m8(r)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m8$Test($r, $t);
      if (xtc::oop::__Test::$R1 == $o) {
        std::cout << __rt::literal("PASS r.m8(t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m8(t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m8$Rest($r, $r);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS r.m8(r)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m8(r)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m8$Test$Test($r, $t, $t);
      if (xtc::oop::__Test::$R3 == $o) {
        std::cout << __rt::literal("PASS r.m8(t, t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m8(t, t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m8$Test$Test($r, $tr, $t);
      if (xtc::oop::__Test::$R3 == $o) {
        std::cout << __rt::literal("PASS r.m8(tr, t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m8(tr, t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m8$Rest$Test($r, $r, $t);
      if (xtc::oop::__Test::$R4 == $o) {
        std::cout << __rt::literal("PASS r.m8(r, t)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m8(r, t)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m9$int16_t($r, $n);
      if (xtc::oop::__Test::$R1 == $o) {
        std::cout << __rt::literal("PASS r.m9(n)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m9(n)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m9$int32_t($r, $n + $n);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS r.m9(n+n)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m9(n+n)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m9$int64_t($r, $n + 5l);
      if (xtc::oop::__Test::$R3 == $o) {
        std::cout << __rt::literal("PASS r.m9(n+5l)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m9(n+5l)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m10$int32_t($r, $n);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS r.m10(n)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m10(n)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($r);
      $o = $r->__vptr->m10$int32_t($r, $n + $n);
      if (xtc::oop::__Test::$R2 == $o) {
        std::cout << __rt::literal("PASS r.m10(n+n)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL r.m10(n+n)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($t);
      Class $k1 = $t->__vptr->getClass$void($t);
      __rt::checkNotNull($r);
      Class $k2 = $r->__vptr->getClass$void($r);
      if ($k1 != $k2) {
        std::cout << __rt::literal("PASS k1 != k2") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL K1 != k2") << std::endl;
      }
      $test++;
      __rt::checkNotNull($k1);
      if (({
        String c = $k1->__vptr->getName$void($k1);
        __rt::checkNotNull(c);
        c->__vptr->equals$Object(c, __rt::literal("xtc.oop.Test"));
      })) {
        std::cout << __rt::literal("PASS k1.getName().equals(\"xtc.oop.Test\")") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL k1.getName().equals(\"xtc.oop.Test\")") << std::endl;
      }
      $test++;
      __rt::checkNotNull($k1);
      if (__rt::literal("xtc.oop.Test")->__vptr->equals$Object(__rt::literal("xtc.oop.Test"), $k1->__vptr->getName$void($k1))) {
        std::cout << __rt::literal("PASS \"xtc.oop.Test\".equals(k1.getName())") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL \"xtc.oop.Test\".equals(k1.getName())") << std::endl;
      }
      $test++;
      __rt::checkNotNull($k1);
      if (({
        String c = $k1->__vptr->toString$void($k1);
        __rt::checkNotNull(c);
        c->__vptr->equals$Object(c, __rt::literal("class xtc.oop.Test"));
      })) {
        std::cout << __rt::literal("PASS k1.toString().equals(\"class xtc.oop.Test\")") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL k1.toString().equals(\"class xtc.oop.Test\")") << std::endl;
      }
      $test++;
      __rt::checkNotNull($k1);
      if ((!$k1->__vptr->equals$Object($k1, $k2))) {
        std::cout << __rt::literal("PASS ! k1.equals(k2)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL ! k1.equals(k2)") << std::endl;
      }
      $test++;
      __rt::checkNotNull($k2);
      $k2 = $k2->__vptr->getSuperclass$void($k2);
      if ($k1 == $k2) {
        std::cout << __rt::literal("PASS k1 == k2.super()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL K1 == k2.super()") << std::endl;
      }
      $test++;
      __rt::checkNotNull($k1);
      if ($k1->__vptr->equals$Object($k1, $k2)) {
        std::cout << __rt::literal("PASS k1.equals(k2.super())") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL k1.equals(k2.super())") << std::endl;
      }
      $test++;
      __rt::checkNotNull($k1);
      $k1 = $k1->__vptr->getSuperclass$void($k1);
      __rt::checkNotNull($k2);
      $k2 = $k2->__vptr->getSuperclass$void($k2);
      if ($k1 == $k2) {
        std::cout << __rt::literal("PASS k1.super() == k2.super().super()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL K1.super() == k2.super().super()") << std::endl;
      }
      $test++;
      __rt::checkNotNull($k1);
      if ($k1->__vptr->equals$Object($k1, $k2)) {
        std::cout << __rt::literal("PASS k1.super().equals(k2.super().super())") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL k1.super().equals(k2.super().super())") << std::endl;
      }
      $test++;
      __rt::checkNotNull($k1);
      $k1 = $k1->__vptr->getSuperclass$void($k1);
      if (__rt::null() == $k1) {
        std::cout << __rt::literal("PASS null == k1.super().super()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL null == k1.super().super()") << std::endl;
      }
      $test++;
      $s1 = __rt::literal("Hello Kitty #1");
      $s2 = __rt::literal("Hello Kitty #1");
      __rt::checkNotNull($s1);
      if ($s1->__vptr->equals$Object($s1, $s2)) {
        std::cout << __rt::literal("PASS s1.equals(String)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL s1.equals(String)") << std::endl;
      }
      $test++;
      $s2 = ({
        std::ostringstream sout;
        sout << __rt::literal("Hel") << __rt::literal("lo Kitty #1");
        String s = new __String(sout.str());
        s;
      });
      __rt::checkNotNull($s1);
      if ($s1->__vptr->equals$Object($s1, $s2)) {
        std::cout << __rt::literal("PASS s1.equals(String + String)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL s1.equals(String + String)") << std::endl;
      }
      $test++;
      $s2 = ({
        std::ostringstream sout;
        sout << ({
          String x;
          String y = ({
            std::ostringstream sout;
            sout << __rt::literal("He") << __rt::literal("ll");
            String s = new __String(sout.str());
            s;
          });
          if (__rt::null() == y)
            x = __rt::literal("null");
          else
            x = y;
          x;
        }) << __rt::literal("o Kitty #1");
        String s = new __String(sout.str());
        s;
      });
      __rt::checkNotNull($s1);
      if ($s1->__vptr->equals$Object($s1, $s2)) {
        std::cout << __rt::literal("PASS s1.equals(String + String + String)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL s1.equals(String + String + String)") << std::endl;
      }
      $test++;
      $s2 = ({
        std::ostringstream sout;
        sout << __rt::literal("Hello Kitty #") << (1);
        String s = new __String(sout.str());
        s;
      });
      __rt::checkNotNull($s1);
      if ($s1->__vptr->equals$Object($s1, $s2)) {
        std::cout << __rt::literal("PASS s1.equals(String + int)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL s1.equals(String + int)") << std::endl;
      }
      $test++;
      $s2 = ({
        std::ostringstream sout;
        sout << __rt::literal("Hello Kitty #") << ('1');
        String s = new __String(sout.str());
        s;
      });
      __rt::checkNotNull($s1);
      if ($s1->__vptr->equals$Object($s1, $s2)) {
        std::cout << __rt::literal("PASS s1.equals(String + char)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL s1.equals(String + char)") << std::endl;
      }
      $test++;
      $s2 = ({
        std::ostringstream sout;
        sout << ((char)(72)) << __rt::literal("ello Kitty #1");
        String s = new __String(sout.str());
        s;
      });
      __rt::checkNotNull($s1);
      if ($s1->__vptr->equals$Object($s1, $s2)) {
        std::cout << __rt::literal("PASS s1.equals(char + String)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL s1.equals(char + String)") << std::endl;
      }
      $test++;
      char $c = 72;
      $s2 = ({
        std::ostringstream sout;
        sout << ($c) << __rt::literal("ello Kitty #1");
        String s = new __String(sout.str());
        s;
      });
      __rt::checkNotNull($s1);
      if ($s1->__vptr->equals$Object($s1, $s2)) {
        std::cout << __rt::literal("PASS s1.equals(char + String)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL s1.equals(char + String)") << std::endl;
      }
      $test++;
      $s2 = ({
        std::ostringstream sout;
        sout << ('H') << __rt::literal("ello Kitty #1");
        String s = new __String(sout.str());
        s;
      });
      __rt::checkNotNull($s1);
      if ($s1->__vptr->equals$Object($s1, $s2)) {
        std::cout << __rt::literal("PASS s1.equals(char + String)") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL s1.equals(char + String)") << std::endl;
      }
      $test++;
      __rt::Ptr<__rt::Array<int16_t > > $a0 = new __rt::Array<int16_t >(0);
      if ($a0->length == 0) {
        std::cout << __rt::literal("PASS short[0].length") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL short[0].length") << std::endl;
      }
      $test++;
      __rt::Ptr<__rt::Array<int16_t > > $a1 = new __rt::Array<int16_t >(1);
      if ($a1->length == 1) {
        std::cout << __rt::literal("PASS short[1].length") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL short[1].length") << std::endl;
      }
      $test++;
      __rt::Ptr<__rt::Array<int16_t > > $a2 = new __rt::Array<int16_t >(2);
      if ($a2->length == 2) {
        std::cout << __rt::literal("PASS short[2].length") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL short[2].length") << std::endl;
      }
      $test++;
      __rt::checkNotNull($a1);
      __rt::checkNotNull($a2);
      if ((((*$a1)[0] == 0 && (*$a2)[0] == 0) && (*$a2)[1] == 0)) {
        std::cout << __rt::literal("PASS short[i] == 0") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL short[i] == 0") << std::endl;
      }
      $test++;
      __rt::checkNotNull($a1);
      (*$a1)[0] = (int16_t)(32768);
      __rt::checkNotNull($a1);
      if ((*$a1)[0] == -32768) {
        std::cout << __rt::literal("PASS short[0] = (short)32768") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL short[0] = (short)32768") << std::endl;
      }
      $test++;
      __rt::checkNotNull($a0);
      if (({
        String c = ({
          Class c = $a0->__vptr->getClass$void($a0);
          __rt::checkNotNull(c);
          c->__vptr->getName$void(c);
        });
        __rt::checkNotNull(c);
        c->__vptr->equals$Object(c, __rt::literal("[S"));
      })) {
        std::cout << __rt::literal("PASS short[0].getClass().getName()") << std::endl;
        $success++;
      } else {
        std::cout << __rt::literal("FAIL short[0].getClass().getName()") << std::endl;
      }
      $test++;
      std::cout << std::endl;
      std::cout << ({
        String x;
        String y = ({
          std::ostringstream sout;
          sout << ({
            String x;
            String y = ({
              std::ostringstream sout;
              sout << ({
                String x;
                String y = ({
                  std::ostringstream sout;
                  sout << ($success) << __rt::literal(" out of ");
                  String s = new __String(sout.str());
                  s;
                });
                if (__rt::null() == y)
                  x = __rt::literal("null");
                else
                  x = y;
                x;
              }) << ($test);
              String s = new __String(sout.str());
              s;
            });
            if (__rt::null() == y)
              x = __rt::literal("null");
            else
              x = y;
            x;
          }) << __rt::literal(" tests have passed.");
          String s = new __String(sout.str());
          s;
        });
        if (__rt::null() == y)
          x = __rt::literal("null");
        else
          x = y;
        x;
      }) << std::endl;
    }

    Class __Test::__class() {
      static Class k = new __Class(__rt::literal("xtc.oop.Test"), __Object::__class());
      return k;
    }

    __Test_VT __Test::__vtable;


    const xtc::oop::Test __Rest::__super = new xtc::oop::__Test();

    __Rest::__Rest()
    : __vptr(&__vtable) {}

    Rest __Rest::Rest$void(Rest __this) {
      if (__rt::null() == __this)
        __this = new __Rest();
      xtc::oop::__Test::Test$void(__this);
      __this->$count = 0;
      __this->$round = 0;
      __this->$round = 0;
      return __this;
    }

    void __Rest::__delete(__Rest* __this) {
      delete __this;
    }

    Object __Rest::m1$void(Rest __this) {
      return xtc::oop::__Test::$R2;
    }

    Object __Rest::m2$void() {
      return xtc::oop::__Test::$R4;
    }

    Test __Rest::m4$void(Rest __this) {
      __this->$round++;
      return __this;
    }

    Object __Rest::m7$Test(Rest __this, Test $t) {
      return xtc::oop::__Test::$R3;
    }

    Object __Rest::m9$int16_t(Rest __this, int16_t $n1) {
      return xtc::oop::__Test::$R1;
    }

    Object __Rest::m9$int32_t(Rest __this, int32_t $n1) {
      return xtc::oop::__Test::$R2;
    }

    Object __Rest::m9$int64_t(Rest __this, int64_t $n1) {
      return xtc::oop::__Test::$R3;
    }

    Object __Rest::m10$int32_t(Rest __this, int32_t $n1) {
      return xtc::oop::__Test::$R2;
    }

    Object __Rest::m10$int64_t(Rest __this, int64_t $n1) {
      return xtc::oop::__Test::$R3;
    }

    int32_t __Rest::hashCode$void(Rest __this) {
      return 7353;
    }

    void __Rest::main$array1_String(__rt::Ptr<__rt::Array<String > > $args) {
      std::cout << __rt::literal("FAIL Test.main()") << std::endl;
      std::cout << std::endl;
      std::cout << __rt::literal("0 out of n tests have passed.") << std::endl;
    }

    Class __Rest::__class() {
      static Class k = new __Class(__rt::literal("xtc.oop.Rest"), xtc::oop::__Test::__class());
      return k;
    }

    __Rest_VT __Rest::__vtable;


  }
}
int main(int argc, char *argv[]) {
  __rt::Ptr<__rt::Array<String> > args = new __rt::Array<String>(argc-1);
  for (int i = 1; i < argc; i++) {
    (*args)[i-1] = __rt::literal(argv[i]);
  }
  xtc::oop::__Test::main$array1_String(args);
}
