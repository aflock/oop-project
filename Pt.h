#include <stdint.h>
#include <string>
#include "java_lang.h"
#include <cstring>

using namespace java::lang;
//forward decls of shit
struct _Pt;
struct _Pt_VT;
struct _ColorPt;
struct _ColorPt_VT;

//typeDef
typedef _Pt* Pt;
typedef _ColorPt* ColorPt;

struct _Pt {
//Data fields
  _Pt_VT* __vptr;

//Constructors
  _Pt();

//Forward declaration of methods
  static int32_t  hashCode (Pt);
  static bool  equals (Pt, Object);
  static Class  getClass (Pt);
  static String  toString (Pt);

  static Class __class();
  static _Pt_VT __vtable;
};

// The vtable layout for Pt.
struct _Pt_VT {
  Class __isa;
  int32_t (*hashCode)(Pt);
  bool (*equals)(Pt, Object);
  Class (*getClass)(Pt);
  String (*toString)(Pt);

  _Pt_VT()
  : __isa(_Pt::__class()),
    hashCode((int32_t(*)(Pt))&__Object::hashCode),
    equals((bool(*)(Pt, Object))&__Object::equals),
    getClass((Class(*)(Pt))&__Object::getClass),
    toString((String(*)(Pt))&__Object::toString) {
  }
};
struct _ColorPt {
//Data fields
  _ColorPt_VT* __vptr;
   const String color;

//Constructors
  _ColorPt( String color);

//Forward declaration of methods
  static int32_t  hashCode (ColorPt);
  static bool  equals (ColorPt, Object);
  static Class  getClass (ColorPt);
  static String  toString (ColorPt);
  static String  getColor (ColorPt);	
  static void  setColor (ColorPt, String);	
  static void  addition (ColorPt, int, int);	

  static Class __class();
  static _ColorPt_VT __vtable;
};

// The vtable layout for ColorPt.
struct _ColorPt_VT {
  Class __isa;
  int32_t (*hashCode)(ColorPt);
  bool (*equals)(ColorPt, Object);
  Class (*getClass)(ColorPt);
  String (*toString)(ColorPt);
  String (*getColor)(ColorPt);	
  void (*setColor)(ColorPt, String);	
  void (*addition)(ColorPt, int, int);	

  _ColorPt_VT()
  : __isa(_ColorPt::__class()),
    hashCode((int32_t(*)(ColorPt))&_Pt::hashCode),
    equals((bool(*)(ColorPt, Object))&_Pt::equals),
    getClass((Class(*)(ColorPt))&_Pt::getClass),
    toString((String(*)(ColorPt))&_Pt::toString),
    getColor(&_ColorPt::getColor),
    setColor(&_ColorPt::setColor),
    addition(&_ColorPt::addition) {
  }
};

