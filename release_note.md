# WAsys_pojo_http_data_servlet3_wrapper Release Note

repo: https://github.com/911992/WAsys_pojo_http_data_servlet3_wrapper  
Author: [911992](https://github.com/911992)  
*(NOTE: following list carries mentionable(not all) changes. For detailed changes, check source code(s))*  

**0.4.1** (Apr 19, 2021)

0. Repo
    * Updated `pom.xml` file
        * Artifact to version `0.4.1`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.4.1`

<hr/>

**0.4.0** (Apr 14, 2021)

0. Repo
    * Updated `pom.xml` file
        * Artifact to version `0.4.0`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.4.0`

<hr/>

**0.3.9** (Sept 9, 2020)

0. Repo
    * Updated `pom.xml` file
        * Artifact to version `0.3.9`
        * Updated the dependency of `WAsys_simple_generic_object_pool` to `0.5.9`
        * Updated the dependency of `WAsys_Java_type_util` to `0.1.9`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.3.9`

<hr/>

**0.3.5** (Sept 1, 2020)

0. Similar update related to `0.3.1`
1. `(－‸ლ)` <s>becasue of `WAsys_pojo_http_data` version `0.3.5`</s>
2. Repo
    * Updated `pom.xml` file
        * Artifact to version `0.3.5`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.3.5`

<hr/>

**0.3.3** (Aug 29, 2020)

0. Changes related to `WAsys_simple_generic_object_pool` API change version `0.5.7`, and `WAsys_pojo_http_data` version `0.3.3`
1. `Source_Code::Servlet_Request_Data_Handler`
    * Removed import of `Pool_Context`(since it's no more)
    * Creating the pooled internal array-list by `Generic_Object_Pool` 
4. Diagrams
    * Updated class diagram (check changes [here](./_docs/diagram/class_diagram_release_note.md))
5. Repo
    * Updated `pom.xml` file
        * Added copyright literal for generated javadoc(plugin)
        * Artifact to version `0.3.3`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.3.3`
        * Updated the dependency of `WAsys_simple_generic_object_pool` to `0.5.7`

<hr/>

**0.3.1** (Aug 24, 2020)

0. `(－‸ლ)` <s>becasue of `WAsys_pojo_http_data` version `0.2.9`</s>
1. Repo
    * Updated `pom.xml` file
        * Artifact to version `0.3.1`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.3.1`

<hr/>

**0.2.9** (Aug 23, 2020)

0. Changes related to `WAsys_simple_generic_object_pool` API change version `0.5.1`
1. Repo now depends on [WAsys_Java_type_util](https://github.com/911992/WAsys_Java_type_util)
2. `Source_Code::Servlet_Request_Data_Handler`
    * API sync with `WAsys_simple_generic_object_pool` v0.5.1 changes
    * Changed `wasys.lib.generic_object_pool.api.Object_Factory` to `wasys.lib.java_type_util.reflect.type_sig.Object_Factory`
3. Diagrams
    * Updated abstract composite struct diagram (check changes [here](./_docs/diagram/abstract_composite_struct_diagram_release_note.md))
    * Updated class diagram (check changes [here](./_docs/diagram/class_diagram_release_note.md))
    * Updated composite structure diagram (check changes [here](./_docs/diagram/composite_struct_diagram_release_note.md))
4. Repo
    * Updated `README.md`file
        * Added `WAsys_Java_type_util` as requirements
    * Updated `pom.xml` file
        * Artifact to version `0.2.9`
        * Updated the dependency of `WAsys_simple_generic_object_pool` to `0.5.1`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.2.9`
        * Added `WAsys_Java_type_util` dependency with version `0.1.7`

<hr/>

**0.2.5** (Aug 13, 2020)

0. Updated the depedency of [WAsys_pojo_http_data](https://github.com/911992/WAsys_pojo_http_data) to `v0.2.5` (20200813)
1. `Source_Code::Servlet_Request_Data_Handler`
    * Some documentation fixes
1. Repo
    * Updated `pom.xml`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.2.5`
        * Updated the artifact to `0.2.5`
        * Removed completed tasks from TODOs section
    * Updated `README.md` file
        * Updated the maven repositiory to `0.2.5`

<hr/>

**0.2.2** (Jun 24, 2020)

0. Updated the depedency of [WAsys_pojo_http_data](https://github.com/911992/WAsys_pojo_http_data) to `v0.2.1` (20200724)
1. Repo
    * Updated `pom.xml`
        * Updated the dependency of `WAsys_pojo_http_data` to `0.2.1`
        * Updated the artifact to `0.2.2` ( to avoid confusion with `0.2.0`)
    * Updated `README.md` file
        * Updated the maven repositiory to `0.2.2`

<hr/>

**0.2.1** (Jun 8, 2020)

0. Added the sample usage repo link (https://github.com/911992/WAsys_pojo_http_data_servlet3_wrapper_test)
1. Repo
    * Updated `README.md` file
        * Added "Sample Usage" section, and link to the repo
        * Marked the "Usage example" in "TODOs" section checked

<hr/>

**0.2.0** (Jun 6, 2020)

0. Lib/artifact now is available from **Maven** central repository (⌐■_■)
1. Project now follows a std Maven project layout.
    * Moved all source files from `src` to `src/main/java` folder
2. `Source_Code::all`
    * Fixed and reworked documentation
3. `Source_Code::Servlet_Request_Data_Handler`
    * Changed the way `Scanner` class is initialized in `instream_to_str()` method, now using different constructor.
    * Changed `CTX_POOL_POOL_INIT_LEN_KEY`, `CTX_POOL_POOL_MAX_LEN_KEY`, and `CTX_POOL_POOL_FULL_POL_KEY` fields names and their values
        * `CTX_POOL_POOL_INIT_LEN_KEY` -> `Servlet_Request_Data_Handler_POOL_INIT_LEN_KEY`
        * `CTX_POOL_POOL_MAX_LEN_KEY` -> `Servlet_Request_Data_Handler_POOL_MAX_LEN_KEY`
        * `CTX_POOL_POOL_FULL_POL_KEY` -> `Servlet_Request_Data_Handler_POOL_FULL_POL_KEY`
4. Added `package-info.java` file for package `wasys.lib.pojo_http_data.servlet3_wrapper`
5. Diagrams
    * Updated the class diagram (changes could be found [here](./_docs/diagram/class_diagram_release_note.md))
6. Repo
    * Added `pom.xml` maven file
    * Added `README.txt`, and `LICENSE.txt` files
    * Added `target`(as maven output) to `.gitignore`
    * Updated `README.md` file
        * Added Maven Repository section *(another victory for greater good)*, same on "TODOs" as a checked list.
        * Updated **`Servlet_Request_Data_Handler` Internal Pool Initializing** section.
            * Fixed the sample `web.xml`, with updated key values of `Servlet_Request_Data_Handler`

<hr/>

**0.1.1** (May 31, 2020)

0. `Source_Code::Servlet_Request_Data_Handler`
    * *(deadly stupid bug)*, fixed the check at `get_param_at()` method, about validating if a param is in bound of out-of-form-data params

<hr/>

**Initial Release 0.1** (May 20, 2020)