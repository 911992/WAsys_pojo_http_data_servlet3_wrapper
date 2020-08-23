# Class Diagram Version History
repo: https://github.com/911992/WAsys_pojo_http_data_servlet3_wrapper  
file: [class_diagram](./class_diagram.svg)  
Author: [911992](https://github.com/911992)  

**v0.2.9** (Aug 23, 2020)

* Changes related to `WAsys_simple_generic_object_pool` API change version `0.5.1`
* Removed `Object_Factory` from *WAsys Generic Object Pool* componenet (as version 0.5.1 of related repo)
* Added dependent *Type Signature* componenet from `wasys::lib::java_type_util`
* Nested `_Factory` class of `Servlet_Request_Data_Handler` now templated and implements correct `Object_Factory`
* `E` and `J` on-page references are now points to correct `Object_Factory`/type

<hr/>

**v0.2.0** (Jun 6, 2020)

* Changed `CTX_POOL_POOL_INIT_LEN_KEY`, `CTX_POOL_POOL_MAX_LEN_KEY`, and `CTX_POOL_POOL_FULL_POL_KEY` fields names and their values in class `Servlet_Request_Data_Handler`
        * `CTX_POOL_POOL_INIT_LEN_KEY` -> `Servlet_Request_Data_Handler_POOL_INIT_LEN_KEY`
        * `CTX_POOL_POOL_MAX_LEN_KEY` -> `Servlet_Request_Data_Handler_POOL_MAX_LEN_KEY`
        * `CTX_POOL_POOL_FULL_POL_KEY` -> `Servlet_Request_Data_Handler_POOL_FULL_POL_KEY`
* Added missed association to `Charset` class from `Servlet_Request_Data_Handler`

<hr/>

**v0.1** (May 20, 2020)

* Initial release