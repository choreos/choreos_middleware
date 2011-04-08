<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>CHOReOS Console</title>
<link rel="stylesheet" href="/choreos-web/css/screen.css" type="text/css"
	media="screen" title="default" />
<!--[if IE]>
<link rel="stylesheet" media="all" type="text/css" href="/choreos-web/css/pro_dropline_ie.css" />
<![endif]-->

<!--  jquery core -->
<script src="/choreos-web/js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>

<!--  checkbox styling script -->
<script src="/choreos-web/js/jquery/ui.core.js" type="text/javascript"></script>
<script src="/choreos-web/js/jquery/ui.checkbox.js" type="text/javascript"></script>
<script src="/choreos-web/js/jquery/jquery.bind.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$('input').checkBox();
		$('#toggle-all').click(function() {
			$('#toggle-all').toggleClass('toggle-checked');
			$('#mainform input[type=checkbox]').checkBox('toggle');
			return false;
		});
	});
</script>


<![if !IE 7]>

<!--  styled select box script version 1 -->
<script src="/choreos-web/js/jquery/jquery.selectbox-0.5.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.styledselect').selectbox({
			inputClass : "selectbox_styled"
		});
	});
</script>


<![endif]>


<!--  styled select box script version 2 -->
<script src="/choreos-web/js/jquery/jquery.selectbox-0.5_style_2.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.styledselect_form_1').selectbox({
			inputClass : "styledselect_form_1"
		});
		$('.styledselect_form_2').selectbox({
			inputClass : "styledselect_form_2"
		});
	});
</script>

<!--  styled select box script version 3 -->
<script src="/choreos-web/js/jquery/jquery.selectbox-0.5_style_2.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.styledselect_pages').selectbox({
			inputClass : "styledselect_pages"
		});
	});
</script>

<!--  styled file upload script -->
<script src="/choreos-web/js/jquery/jquery.filestyle.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
	$(function() {
		$("input.file_1").filestyle({
			image : "images/forms/upload_file.gif",
			imageheight : 29,
			imagewidth : 78,
			width : 300
		});
	});
</script>

<!-- Custom jquery scripts -->
<script src="/choreos-web/js/jquery/custom_jquery.js" type="text/javascript"></script>

<!-- Tooltips -->
<script src="/choreos-web/js/jquery/jquery.tooltip.js" type="text/javascript"></script>
<script src="/choreos-web/js/jquery/jquery.dimensions.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$('a.info-tooltip ').tooltip({
			track : true,
			delay : 0,
			fixPNG : true,
			showURL : false,
			showBody : " - ",
			top : -35,
			left : 5
		});
	});
</script>

<!--  date picker script -->
<link rel="stylesheet" href="css/datePicker.css" type="text/css" />
<script src="/choreos-web/js/jquery/date.js" type="text/javascript"></script>
<script src="/choreos-web/js/jquery/jquery.datePicker.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8">
	$(function() {

		// initialise the "Select date" link
		$('#date-pick').datePicker(
		// associate the link with a date picker
		{
			createButton : false,
			startDate : '01/01/2005',
			endDate : '31/12/2020'
		}).bind(
		// when the link is clicked display the date picker
		'click', function() {
			updateSelects($(this).dpGetSelected()[0]);
			$(this).dpDisplay();
			return false;
		}).bind(
		// when a date is selected update the SELECTs
		'dateSelected', function(e, selectedDate, $td, state) {
			updateSelects(selectedDate);
		}).bind('dpClosed', function(e, selected) {
			updateSelects(selected[0]);
		});

		var updateSelects = function(selectedDate) {
			var selectedDate = new Date(selectedDate);
			$('#d option[value=' + selectedDate.getDate() + ']').attr(
					'selected', 'selected');
			$('#m option[value=' + (selectedDate.getMonth() + 1) + ']').attr(
					'selected', 'selected');
			$('#y option[value=' + (selectedDate.getFullYear()) + ']').attr(
					'selected', 'selected');
		}
		// listen for when the selects are changed and update the picker
		$('#d, #m, #y').bind('change', function() {
			var d = new Date($('#y').val(), $('#m').val() - 1, $('#d').val());
			$('#date-pick').dpSetSelected(d.asString());
		});

		// default the position of the selects to today
		var today = new Date();
		updateSelects(today.getTime());

		// and update the datePicker to reflect it...
		$('#d').trigger('change');
	});
</script>

<!-- MUST BE THE LAST SCRIPT IN <HEAD></HEAD></HEAD> png fix -->
<script src="/choreos-web/js/jquery/jquery.pngFix.pack.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(document).pngFix();
	});
</script>
</head>
<body>
	<!-- Start: page-top-outer -->
	<div id="page-top-outer">

		<!-- Start: page-top -->
		<div id="page-top">

			<!-- start logo -->
			<div id="logo">
				<a href="">CHOReOS</a>
			</div>
			<!-- end logo -->

			<!--  start top-search -->
			<!--  end top-search -->
			<div class="clear"></div>

		</div>
		<!-- End: page-top -->

	</div>
	<!-- End: page-top-outer -->

	<div class="clear">&nbsp;</div>

	<!--  start nav-outer-repeat................................................................................................. START -->
	<div class="nav-outer-repeat">
		<!--  start nav-outer -->
		<div class="nav-outer">

			<!-- start nav-right -->
			<!-- end nav-right -->


			<!--  start nav -->
			<div class="nav">
				<div class="table">

					<ul class="select">
						<li><a href="#nogo"><b>Dashboard</b> <!--[if IE 7]><!-->
						</a> <!--<![endif]--> <!--[if lte IE 6]><table><tr><td><![endif]-->
							<div class="select_sub">
								<ul class="sub">
								</ul>
							</div> <!--[if lte IE 6]></td></tr></table></a><![endif]-->
						</li>
					</ul>

					<div class="nav-divider">&nbsp;</div>

					<ul class="current">
						<li><a href="#nogo"><b>Service Registry</b> <!--[if IE 7]><!--> </a>
							<!--<![endif]--> <!--[if lte IE 6]><table><tr><td><![endif]-->
							<div class="select_sub show">
								<ul class="sub">
									<li><a href="#nogo">List all</a></li>
									<li class="sub_show"><a href="#nogo">Add role
											assignment</a></li>
									<li><a href="#nogo">Delete assignment</a></li>
								</ul>
							</div> <!--[if lte IE 6]></td></tr></table></a><![endif]-->
						</li>
					</ul>

					<div class="nav-divider">&nbsp;</div>

					<ul class="select">
						<li><a href="#nogo"><b>Choreographies</b> <!--[if IE 7]><!-->
						</a> <!--<![endif]--> <!--[if lte IE 6]><table><tr><td><![endif]-->
							<div class="select_sub">
								<ul class="sub">
									<li><a href="#nogo">Enact from BPMN2</a></li>
									<li><a href="#nogo">Load BPEL</a></li>
									<li><a href="#nogo">List</a></li>
								</ul>
							</div> <!--[if lte IE 6]></td></tr></table></a><![endif]-->
						</li>
					</ul>

					<div class="nav-divider">&nbsp;</div>

					<ul class="select">
						<li><a href="#nogo"><b>Messages</b> <!--[if IE 7]><!--> </a> <!--<![endif]-->
							<!--[if lte IE 6]><table><tr><td><![endif]-->
							<div class="select_sub">
								<ul class="sub">
									<li><a href="#nogo">List</a></li>
									<li><a href="#nogo">Search</a></li>
									<li><a href="#nogo">Realtime</a></li>

								</ul>
							</div> <!--[if lte IE 6]></td></tr></table></a><![endif]-->
						</li>
					</ul>

					<div class="nav-divider">&nbsp;</div>

					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
			<!--  start nav -->

		</div>
		<div class="clear"></div>
		<!--  start nav-outer -->
	</div>
	<!--  start nav-outer-repeat................................................... END -->

	<div class="clear"></div>

	<!-- start content-outer -->
	<div id="content-outer">
		<!-- start content -->
		<div id="content">


			<div id="page-heading">
				<h1>Add role assignment</h1>
			</div>


			<table border="0" width="100%" cellpadding="0" cellspacing="0"
				id="content-table">
				<tr>
					<th rowspan="3" class="sized"><img
						src="/choreos-web/images/shared/side_shadowleft.jpg" width="20" height="300"
						alt="" /></th>
					<th class="topleft"></th>
					<td id="tbl-border-top">&nbsp;</td>
					<th class="topright"></th>
					<th rowspan="3" class="sized"><img
						src="/choreos-web/images/shared/side_shadowright.jpg" width="20" height="300"
						alt="" /></th>
				</tr>
				<tr>
					<td id="tbl-border-left"></td>
					<td>
						<!--  start content-table-inner -->
						<div id="content-table-inner">

							<table border="0" width="100%" cellpadding="0" cellspacing="0">
								<tr valign="top">
									<td>
										<!--  start step-holder -->
										<form action="<c:url value='/roleAssignments/add'/>">
											<table border="0" cellpadding="0" cellspacing="0"
												id="id-form">
												<tr>
													<th valign="top">Role:</th>
													<td><input type="text" class="inp-form" name="roleAssignment.role" /></td>
													<td></td>
												</tr>
												<tr>
													<th valign="top">URI:</th>
													<td><input type="text" name="roleAssignment.uri" class="inp-form"/></td>
													<!-- <td>
														<div class="error-left"></div>
														<div class="error-inner">This field is required.</div>
													</td> -->
												</tr>
												<tr>
													<th>&nbsp;</th>
													<td valign="top"><input type="submit" value=""
														class="form-submit" /> <input type="reset" value=""
														class="form-reset" />
													</td>
													<td></td>
												</tr>
											</table>
										</form> <!-- end id-form  -->
									</td>
									<td>
										<!--  start related-activities -->
										<div id="related-activities">

											<!--  start related-act-top -->
											<div id="related-act-top">
												<img src="/choreos-web/images/forms/header_related_act.gif" width="271"
													height="43" alt="" />
											</div>
											<!-- end related-act-top -->

											<!--  start related-act-bottom -->
											<div id="related-act-bottom">

												<!--  start related-act-inner -->
												<div id="related-act-inner">

													<div class="left">
														<a href=""><img src="/choreos-web/images/forms/icon_plus.gif"
															width="21" height="21" alt="" /> </a>
													</div>
													<div class="right">
														<h5>Add another assignment</h5>
														Lorem ipsum dolor sit amet consectetur adipisicing elitsed
														do eiusmod tempor.
														<ul class="greyarrow">
															<li><a href="">Click here to visit</a></li>
															<li><a href="">Click here to visit</a>
															</li>
														</ul>
													</div>

													<div class="clear"></div>
													<div class="lines-dotted-short"></div>

													<div class="left">
														<a href=""><img src="/choreos-web/images/forms/icon_minus.gif"
															width="21" height="21" alt="" /> </a>
													</div>
													<div class="right">
														<h5>Delete assignments</h5>
														Lorem ipsum dolor sit amet consectetur adipisicing elitsed
														do eiusmod tempor.
														<ul class="greyarrow">
															<li><a href="">Click here to visit</a></li>
															<li><a href="">Click here to visit</a>
															</li>
														</ul>
													</div>

													<div class="clear"></div>
													<div class="lines-dotted-short"></div>

													<div class="left">
														<a href=""><img src="/choreos-web/images/forms/icon_edit.gif"
															width="21" height="21" alt="" /> </a>
													</div>
													<div class="right">
														<h5>List assignments</h5>
														Lorem ipsum dolor sit amet consectetur adipisicing elitsed
														do eiusmod tempor.
														<ul class="greyarrow">
															<li><a href="">Click here to visit</a></li>
															<li><a href="">Click here to visit</a>
															</li>
														</ul>
													</div>
													<div class="clear"></div>

												</div>
												<!-- end related-act-inner -->
												<div class="clear"></div>

											</div>
											<!-- end related-act-bottom -->

										</div> <!-- end related-activities -->
									</td>
								</tr>
								<tr>
									<td><img src="/choreos-web/images/shared/blank.gif" width="695"
										height="1" alt="blank" /></td>
									<td></td>
								</tr>
							</table>

							<div class="clear"></div>


						</div> <!--  end content-table-inner  -->
					</td>
					<td id="tbl-border-right"></td>
				</tr>
				<tr>
					<th class="sized bottomleft"></th>
					<td id="tbl-border-bottom">&nbsp;</td>
					<th class="sized bottomright"></th>
				</tr>
			</table>


			<div class="clear">&nbsp;</div>

		</div>
		<!--  end content -->
		<div class="clear">&nbsp;</div>
	</div>
	<!--  end content-outer -->


	<div class="clear">&nbsp;</div>

	<!-- start footer -->
	<div id="footer">
		<!--  start footer-left -->
		<div id="footer-left">
			Admin Skin &copy; Copyright Internet Dreams Ltd.
			All rights reserved.
		</div>
		<!--  end footer-left -->
		<div class="clear">&nbsp;</div>
	</div>
	<!-- end footer -->

</body>
</html>



