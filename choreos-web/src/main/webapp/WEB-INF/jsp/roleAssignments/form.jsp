<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-heading">
	<h1>Add role assignment</h1>
</div>

<table border="0" width="100%" cellpadding="0" cellspacing="0"
	id="content-table">
	<tr>
		<th rowspan="3" class="sized"><img
			src="/choreos-web/images/shared/side_shadowleft.jpg" width="20"
			height="300" alt="" />
		</th>
		<th class="topleft"></th>
		<td id="tbl-border-top">&nbsp;</td>
		<th class="topright"></th>
		<th rowspan="3" class="sized"><img
			src="/choreos-web/images/shared/side_shadowright.jpg" width="20"
			height="300" alt="" />
		</th>
	</tr>
	<tr>
		<td id="tbl-border-left"></td>
		<td>
			<!--  start content-table-inner -->
			<div id="content-table-inner">

				<table border="0" width="100%" cellpadding="0" cellspacing="0">
					<tr valign="top">
						<td>
							<div style="color: red; margin-bottom: 2em">
								<c:forEach var="error" items="${errors}">
	    							${error.category}: ${error.message}<br />
								</c:forEach>
							</div>
							<!--  start step-holder -->
							<form action="<c:url value='/roleAssignments/add'/>">
								<table border="0" cellpadding="0" cellspacing="0" id="id-form">
									<tr>
										<th valign="top">Role:</th>
										<td><input type="text" class="inp-form"
											name="roleAssignment.role" />
										</td>
										<td></td>
									</tr>
									<tr>
										<th valign="top">URI:</th>
										<td><input type="text" name="roleAssignment.uri"
											class="inp-form" />
										</td>
										<!-- <td>
														<div class="error-left"></div>
														<div class="error-inner">This field is required.</div>
													</td> -->
									</tr>
									<tr>
										<th>&nbsp;</th>
										<td valign="top"><input type="submit" value=""
											class="form-submit" /> <input type="reset" value=""
											class="form-reset" /></td>
										<td></td>
									</tr>
								</table>
							</form> <!-- end id-form  --></td>
						<td>
							<!--  start related-activities -->
							<div id="related-activities">

								<!--  start related-act-top -->
								<div id="related-act-top">
									<img src="/choreos-web/images/forms/header_related_act.gif"
										width="271" height="43" alt="" />
								</div>
								<!-- end related-act-top -->

								<!--  start related-act-bottom -->
								<div id="related-act-bottom">

									<!--  start related-act-inner -->
									<div id="related-act-inner">

										<div class="left">
											<a href=""><img
												src="/choreos-web/images/forms/icon_plus.gif" width="21"
												height="21" alt="" /> </a>
										</div>
										<div class="right">
											<h5>Add another assignment</h5>
											Lorem ipsum dolor sit amet consectetur adipisicing elitsed do
											eiusmod tempor.
											<ul class="greyarrow">
												<li><a href="">Click here to visit</a>
												</li>
												<li><a href="">Click here to visit</a></li>
											</ul>
										</div>

										<div class="clear"></div>
										<div class="lines-dotted-short"></div>

										<div class="left">
											<a href=""><img
												src="/choreos-web/images/forms/icon_minus.gif" width="21"
												height="21" alt="" /> </a>
										</div>
										<div class="right">
											<h5>Delete assignments</h5>
											Lorem ipsum dolor sit amet consectetur adipisicing elitsed do
											eiusmod tempor.
											<ul class="greyarrow">
												<li><a href="">Click here to visit</a>
												</li>
												<li><a href="">Click here to visit</a></li>
											</ul>
										</div>

										<div class="clear"></div>
										<div class="lines-dotted-short"></div>

										<div class="left">
											<a href=""><img
												src="/choreos-web/images/forms/icon_edit.gif" width="21"
												height="21" alt="" /> </a>
										</div>
										<div class="right">
											<h5>List assignments</h5>
											Lorem ipsum dolor sit amet consectetur adipisicing elitsed do
											eiusmod tempor.
											<ul class="greyarrow">
												<li><a href="">Click here to visit</a>
												</li>
												<li><a href="">Click here to visit</a></li>
											</ul>
										</div>
										<div class="clear"></div>

									</div>
									<!-- end related-act-inner -->
									<div class="clear"></div>

								</div>
								<!-- end related-act-bottom -->

							</div> <!-- end related-activities --></td>
					</tr>
					<tr>
						<td><img src="/choreos-web/images/shared/blank.gif"
							width="695" height="1" alt="blank" />
						</td>
						<td></td>
					</tr>
				</table>

				<div class="clear"></div>


			</div> <!--  end content-table-inner  --></td>
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



