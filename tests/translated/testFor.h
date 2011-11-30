#include "java_lang.h"

typedef java::lang::__Class* Class;
typedef java::lang::__Object* Object;
typedef java::lang::__String* String;

struct _testFor;
struct _testFor_VT;


//Absolute typedefs to make below code more readable
typedef _testFor* testFor;

struct _testFor {
//Data fields
_testFor_VT* __vptr;

//Constructors
_testFor(); 


//Forward declaration of methods
static Class __class();
static _testFor_VT __vtable;

};
// The vtable layout for testFor.
struct _testFor_VT {
Class __isa;


_testFor_VT()
: __isa(_testFor::__class()) {
}
};
