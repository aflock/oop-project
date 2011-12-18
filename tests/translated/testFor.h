#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _testFor;
struct _testFor_VT;


//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _testFor> testFor;

struct _testFor {
//Data fields
_testFor_VT* __vptr;

//Constructors
_testFor(); 


//The destructor
static void __delete(_testFor*);

//Forward declaration of methods
static Class __class();
static _testFor_VT __vtable;

};

// The vtable layout for testFor.
struct _testFor_VT {
Class __isa;
void (*__delete)(_testFor*);

_testFor_VT()
: __isa(_testFor::__class()),
__delete(&_testFor::__delete) {
}
};
