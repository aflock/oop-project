#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _testWhile;
struct _testWhile_VT;


//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _testWhile> testWhile;

struct _testWhile {
//Data fields
_testWhile_VT* __vptr;

//Constructors
_testWhile(); 


//The destructor
static void __delete(_testWhile*);

//Forward declaration of methods
static Class __class();
static _testWhile_VT __vtable;

};

// The vtable layout for testWhile.
struct _testWhile_VT {
Class __isa;
void (*__delete)(_testWhile*);

_testWhile_VT()
: __isa(_testWhile::__class()),
__delete(&_testWhile::__delete) {
}
};
