Pretend it is actually `target/`. Because when testing the target directory
is always recreated (IOW for convenience).

Sample failure:

   <testcase classname="TestPkg.UT_2MSVACTIVECANCELLED" name="Unable to run ut_TestPkg.UT_2MSVACTIVECANCELLED" time="0">
       <failure type="Assert Type Unknown" message="ORA-06533 Subscript beyond count"/>
   </testcase>
