#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _testBasicClass;
struct _testBasicClass_VT;


//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _testBasicClass> testBasicClass;

struct _testBasicClass {
//Data fields
_testBasicClass_VT* __vptr;
String name;

//Constructors
_testBasicClass();

//The destructor
static void __delete(_testBasicClass*);

//Forward declaration of methods
static Class __class();
static _testBasicClass_VT __vtable;

};

// The vtable layout for testBasicClass.
struct _testBasicClass_VT {
Class __isa;
void (*__delete)(_testBasicClass*);

_testBasicClass_VT()
: __isa(_testBasicClass::__class()),
__delete(&_testBasicClass::__delete) {
}
};
