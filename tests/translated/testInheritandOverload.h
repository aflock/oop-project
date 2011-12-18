#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _testInheritandOverload;
struct _testInheritandOverload_VT;
struct _Inheriter;
struct _Inheriter_VT;

static int _TypeName::nameOfInt;

//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _testInheritandOverload> testInheritandOverload;
typedef __rt::Ptr< _Inheriter> Inheriter;

struct _testInheritandOverload {
//Data fields
_testInheritandOverload_VT* __vptr;
String name;

//Constructors
_testInheritandOverload();

//The destructor
static void __delete(_testInheritandOverload*);

//Forward declaration of methods
static Class __class();
static _testInheritandOverload_VT __vtable;
static void m(testInheritandOverload);

};

// The vtable layout for testInheritandOverload.
struct _testInheritandOverload_VT {
Class __isa;
void (*__delete)(_testInheritandOverload*);
int32_t (*hashCode)(testInheritandOverload);
bool (*equals)(testInheritandOverload, Object);
Class (*getClass)(testInheritandOverload);
String (*toString)(testInheritandOverload);
void (*m)(testInheritandOverload);

_testInheritandOverload_VT()
: __isa(_testInheritandOverload::__class()),
__delete(&_testInheritandOverload::__delete),
hashCode((int32_t(*)(testInheritandOverload))&java::lang::__Object::hashCode),
equals((bool(*)(testInheritandOverload,Object))&java::lang::__Object::equals),
getClass((Class(*)(testInheritandOverload))&java::lang::__Object::getClass),
toString((String(*)(testInheritandOverload))&java::lang::__Object::toString),
m(&_testInheritandOverload::m) {
}
};
struct _Inheriter {
//Data fields
_Inheriter_VT* __vptr;

//Constructors
_Inheriter();


//The destructor
static void __delete(_Inheriter*);

//Forward declaration of methods
static Class __class();
static _Inheriter_VT __vtable;
static void _m(Inheriter, int32_t);
};

// The vtable layout for Inheriter.
struct _Inheriter_VT {
Class __isa;
void (*__delete)(_Inheriter*);
int32_t (*hashCode)(Inheriter);
bool (*equals)(Inheriter, Object);
Class (*getClass)(Inheriter);
String (*toString)(Inheriter);
void (*m)(Inheriter);
void (*_m)(Inheriter, int32_t);

_Inheriter_VT()
: __isa(_Inheriter::__class()),
__delete(&_Inheriter::__delete),
hashCode((int32_t(*)(Inheriter))&java::lang::__Object::hashCode),
equals((bool(*)(Inheriter,Object))&java::lang::__Object::equals),
getClass((Class(*)(Inheriter))&java::lang::__Object::getClass),
toString((String(*)(Inheriter))&java::lang::__Object::toString),
m((void(*)(Inheriter))&_testInheritandOverload::m),
_m(&_Inheriter::_m) {
}
};
