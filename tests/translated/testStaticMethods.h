#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _testStaticMethods;
struct _testStaticMethods_VT;


//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _testStaticMethods> testStaticMethods;

struct _testStaticMethods {
//Data fields
_testStaticMethods_VT* __vptr;

//Constructors
_testStaticMethods(); 


//The destructor
static void __delete(_testStaticMethods*);

//Forward declaration of methods
static Class __class();
static _testStaticMethods_VT __vtable;
static int getNum();
static int getAge();
static int getCount();
static int getNumber(testStaticMethods);

};

// The vtable layout for testStaticMethods.
struct _testStaticMethods_VT {
Class __isa;
int (*getNumber)(testStaticMethods);
void (*__delete)(_testStaticMethods*);

_testStaticMethods_VT()
: __isa(_testStaticMethods::__class()),
getNumber(&_testStaticMethods::getNumber),
__delete(&_testStaticMethods::__delete) {
}
};
