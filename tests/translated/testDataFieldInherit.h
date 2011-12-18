#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _testDataFieldInherit;
struct _testDataFieldInherit_VT;
struct _testI1;
struct _testI1_VT;
struct _testI2;
struct _testI2_VT;
struct _testI3;
struct _testI3_VT;
struct _testI4;
struct _testI4_VT;


//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _testDataFieldInherit> testDataFieldInherit;
typedef __rt::Ptr< _testI1> testI1;
typedef __rt::Ptr< _testI2> testI2;
typedef __rt::Ptr< _testI3> testI3;
typedef __rt::Ptr< _testI4> testI4;

struct _testDataFieldInherit {
//Data fields
_testDataFieldInherit_VT* __vptr;
String name;

//constructors
_testDataFieldInherit();

//The destructor
static void __delete(_testDataFieldInherit*);

//Forward declaration of methods
static Class __class();
static _testDataFieldInherit_VT __vtable;

};

// The vtable layout for testDataFieldInherit.
struct _testDataFieldInherit_VT {
Class __isa;
void (*__delete)(_testDataFieldInherit*);
int32_t (*hashCode)(testDataFieldInherit);
bool (*equals)(testDataFieldInherit, Object);
Class (*getClass)(testDataFieldInherit);
String (*toString)(testDataFieldInherit);

_testDataFieldInherit_VT()
: __isa(_testDataFieldInherit::__class()),
__delete(&_testDataFieldInherit::__delete),
hashCode((int32_t(*)(testDataFieldInherit))&java::lang::__Object::hashCode),
equals((bool(*)(testDataFieldInherit,Object))&java::lang::__Object::equals),
getClass((Class(*)(testDataFieldInherit))&java::lang::__Object::getClass),
toString((String(*)(testDataFieldInherit))&java::lang::__Object::toString) {
}
};
struct _testI1 {
//Data fields
_testI1_VT* __vptr;
String name;

//constructors
_testI1();

//The destructor
static void __delete(_testI1*);

//Forward declaration of methods
static Class __class();
static _testI1_VT __vtable;
static void test(testI1);
static void test2(testI1);
};

// The vtable layout for testI1.
struct _testI1_VT {
Class __isa;
void (*__delete)(_testI1*);
int32_t (*hashCode)(testI1);
bool (*equals)(testI1, Object);
Class (*getClass)(testI1);
String (*toString)(testI1);
void (*test)(testI1);
void (*test2)(testI1);

_testI1_VT()
: __isa(_testI1::__class()),
__delete(&_testI1::__delete),
hashCode((int32_t(*)(testI1))&java::lang::__Object::hashCode),
equals((bool(*)(testI1,Object))&java::lang::__Object::equals),
getClass((Class(*)(testI1))&java::lang::__Object::getClass),
toString((String(*)(testI1))&java::lang::__Object::toString),
test(&_testI1::test),
test2(&_testI1::test2) {
}
};
struct _testI2 {
//Data fields
_testI2_VT* __vptr;
int32_t name;
String $name;

//constructors
_testI2();

//The destructor
static void __delete(_testI2*);

//Forward declaration of methods
static Class __class();
static _testI2_VT __vtable;
static void test(testI2);
static void test2(testI2);
};

// The vtable layout for testI2.
struct _testI2_VT {
Class __isa;
void (*__delete)(_testI2*);
int32_t (*hashCode)(testI2);
bool (*equals)(testI2, Object);
Class (*getClass)(testI2);
String (*toString)(testI2);
void (*test)(testI2);
void (*test2)(testI2);

_testI2_VT()
: __isa(_testI2::__class()),
__delete(&_testI2::__delete),
hashCode((int32_t(*)(testI2))&java::lang::__Object::hashCode),
equals((bool(*)(testI2,Object))&java::lang::__Object::equals),
getClass((Class(*)(testI2))&java::lang::__Object::getClass),
toString((String(*)(testI2))&java::lang::__Object::toString),
test(&_testI2::test),
test2(&_testI2::test2) {
}
};
struct _testI3 {
//Data fields
_testI3_VT* __vptr;
char name;
int32_t $name;

//constructors
_testI3();

//The destructor
static void __delete(_testI3*);

//Forward declaration of methods
static Class __class();
static _testI3_VT __vtable;
static void test(testI3);
};

// The vtable layout for testI3.
struct _testI3_VT {
Class __isa;
void (*__delete)(_testI3*);
int32_t (*hashCode)(testI3);
bool (*equals)(testI3, Object);
Class (*getClass)(testI3);
String (*toString)(testI3);
void (*test)(testI3);
void (*test2)(testI3);

_testI3_VT()
: __isa(_testI3::__class()),
__delete(&_testI3::__delete),
hashCode((int32_t(*)(testI3))&java::lang::__Object::hashCode),
equals((bool(*)(testI3,Object))&java::lang::__Object::equals),
getClass((Class(*)(testI3))&java::lang::__Object::getClass),
toString((String(*)(testI3))&java::lang::__Object::toString),
test(&_testI3::test),
test2((void(*)(testI3))&_testI2::test2) {
}
};
struct _testI4 {
//Data fields
_testI4_VT* __vptr;
char name;

//constructors
_testI4();

//The destructor
static void __delete(_testI4*);

//Forward declaration of methods
static Class __class();
static _testI4_VT __vtable;
static void test(testI4);
};

// The vtable layout for testI4.
struct _testI4_VT {
Class __isa;
void (*__delete)(_testI4*);
int32_t (*hashCode)(testI4);
bool (*equals)(testI4, Object);
Class (*getClass)(testI4);
String (*toString)(testI4);
void (*test)(testI4);
void (*test2)(testI4);

_testI4_VT()
: __isa(_testI4::__class()),
__delete(&_testI4::__delete),
hashCode((int32_t(*)(testI4))&java::lang::__Object::hashCode),
equals((bool(*)(testI4,Object))&java::lang::__Object::equals),
getClass((Class(*)(testI4))&java::lang::__Object::getClass),
toString((String(*)(testI4))&java::lang::__Object::toString),
test(&_testI4::test),
test2((void(*)(testI4))&_testI2::test2) {
}
};
