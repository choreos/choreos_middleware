#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-service03ea00f3-6c0f-48f2-b03d-31076d583df4" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service03ea00f3-6c0f-48f2-b03d-31076d583df4::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/03ea00f3-6c0f-48f2-b03d-31076d583df4.war]", :immediately
end

ruby_block "remove-service03ea00f3-6c0f-48f2-b03d-31076d583df4" do
  block do
  	node.run_list.remove("recipe[service03ea00f3-6c0f-48f2-b03d-31076d583df4::war]")
  end
  only_if { node.run_list.include?("recipe[service03ea00f3-6c0f-48f2-b03d-31076d583df4::war]") }
end

ruby_block "remove-deactivate-service03ea00f3-6c0f-48f2-b03d-31076d583df4" do
  block do
    node.run_list.remove("recipe[deactivate-service03ea00f3-6c0f-48f2-b03d-31076d583df4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service03ea00f3-6c0f-48f2-b03d-31076d583df4]") }
end
