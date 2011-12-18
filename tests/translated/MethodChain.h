#include "java_lang.h"

typedef __rt::Ptr<java::lang::__Object> Object;
typedef __rt::Ptr<java::lang::__Class> Class;
typedef __rt::Ptr<java::lang::__String> String;

struct _MethodChain;
struct _MethodChain_VT;


//Absolute typedefs to make below code more readable
typedef __rt::Ptr< _MethodChain> MethodChain;

struct _MethodChain {
//Data fields
_MethodChain_VT* __vptr;

//Constructors
_MethodChain(); 


//The destructor
static void __delete(_MethodChain*);

//Forward declaration of methods
static Class __class();
static _MethodChain_VT __vtable;

static MethodChain m(MethodChain);
static MethodChain _m(MethodChain, MethodChain);
};

// The vtable layout for MethodChain.
struct _MethodChain_VT {
Class __isa;
void (*__delete)(_MethodChain*);
int32_t (*hashCode)(MethodChain);
bool (*equals)(MethodChain, Object);
Class (*getClass)(MethodChain);
String (*toString)(MethodChain);
MethodChain (*m)(MethodChain);
MethodChain (*_m)(MethodChain, MethodChain);

_MethodChain_VT()
: __isa(_MethodChain::__class()),
__delete(&_MethodChain::__delete),
hashCode((int32_t(*)(MethodChain))&java::lang::__Object::hashCode),
equals((bool(*)(MethodChain,Object))&java::lang::__Object::equals),
getClass((Class(*)(MethodChain))&java::lang::__Object::getClass),
toString((String(*)(MethodChain))&java::lang::__Object::toString),
m(&_MethodChain::m),
_m(&_MethodChain::_m) {
}
};
