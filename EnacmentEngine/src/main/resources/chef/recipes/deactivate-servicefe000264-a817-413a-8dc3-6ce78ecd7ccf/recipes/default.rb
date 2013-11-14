#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

ruby_block "disable-servicefe000264-a817-413a-8dc3-6ce78ecd7ccf" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicefe000264-a817-413a-8dc3-6ce78ecd7ccf::jar]") }
  notifies :stop, "service[service_fe000264-a817-413a-8dc3-6ce78ecd7ccf_jar]", :immediately
end

ruby_block "remove-servicefe000264-a817-413a-8dc3-6ce78ecd7ccf" do
  block do
  	node.run_list.remove("recipe[servicefe000264-a817-413a-8dc3-6ce78ecd7ccf::jar]")
  end
  only_if { node.run_list.include?("recipe[servicefe000264-a817-413a-8dc3-6ce78ecd7ccf::jar]") }
end

ruby_block "remove-deactivate-servicefe000264-a817-413a-8dc3-6ce78ecd7ccf" do
  block do
    node.run_list.remove("recipe[deactivate-servicefe000264-a817-413a-8dc3-6ce78ecd7ccf]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicefe000264-a817-413a-8dc3-6ce78ecd7ccf]") }
end
