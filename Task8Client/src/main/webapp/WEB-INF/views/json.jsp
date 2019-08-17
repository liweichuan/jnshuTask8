<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>>
<json:object>
    <json:array name="myJson" var="Student" items="${stu}">
        <json:object>
          <json:property name="name" value="${Student.name}"/>
          <json:property name="qq" value="${Student.qq}"/>
          <json:property name="type" value="${Student.type}"/>
          <json:property name="start_time" value="${Student.start_time}"/>
          <json:property name="school" value="${Student.school}"/>
          <json:property name="student_id" value="${Student.student_id}"/>
          <json:property name="link" value="${Student.link}"/>
          <json:property name="wish" value="${Student.wish}"/>
          <json:property name="bro" value="${Student.bro}"/>
          <json:property name="way" value="${Student.way}"/>
          <json:property name="create_time" value="${Student.create_time}"/>
          <json:property name="update_time" value="${Student.update_time}"/>
        </json:object>
    </json:array>
</json:object>