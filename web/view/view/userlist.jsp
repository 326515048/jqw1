<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <title id='Description'>This example shows how to create a Grid from JSON data.</title>
    <link rel="stylesheet" href="../../jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="../../scripts/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxdata.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxgrid.sort.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxgrid.filter.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxgrid.selection.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxgrid.pager.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxpanel.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="../../jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="../../scripts/demos.js"></script>
    <script type="text/javascript">
      $(document).ready(function () {
        var url = "/user/all";

        // prepare the data
        var source =
        {
          datatype: "json",
          datafields: [
            { name: 'uuid', type: 'string' },
            { name: 'userID', type: 'string' },
            { name: 'userName', type: 'string' }
          ],
          id: 'uuid',
          url: url
        };
        var dataAdapter = new $.jqx.dataAdapter(source);

        $("#jqxgrid").jqxGrid(
                {
                  width: 800,
                  source: dataAdapter,
                  sortable: true,
                  //filterable: true,
                  pageable: true,
                  selectionmode: 'checkbox',
                  altrows: true,
                  //columnsresize: true,
                  columns: [
                    { text: 'uuid', datafield: 'uuid', width: 250 },
                    { text: 'uid', datafield: 'userID', width: 250 },
                    { text: 'name', datafield: 'userName', width: 250 }
                  ]
                });
      });
    </script>
  </head>
  <body class='default'>
  <div id='jqxWidget' style="font-size: 13px; font-family: Verdana; float: left;">
    <div id="jqxgrid"></div>
  </div>
  </body>
</html>
