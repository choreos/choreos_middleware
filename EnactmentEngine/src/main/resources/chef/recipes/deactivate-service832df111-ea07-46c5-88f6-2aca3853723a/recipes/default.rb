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

ruby_block "remove-service832df111-ea07-46c5-88f6-2aca3853723a" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service832df111-ea07-46c5-88f6-2aca3853723a::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/832df111-ea07-46c5-88f6-2aca3853723a.war]", :immediately
end

ruby_block "remove-service832df111-ea07-46c5-88f6-2aca3853723a" do
  block do
  	node.run_list.remove("recipe[service832df111-ea07-46c5-88f6-2aca3853723a::war]")
  end
  only_if { node.run_list.include?("recipe[service832df111-ea07-46c5-88f6-2aca3853723a::war]") }
end

ruby_block "remove-deactivate-service832df111-ea07-46c5-88f6-2aca3853723a" do
  block do
    node.run_list.remove("recipe[deactivate-service832df111-ea07-46c5-88f6-2aca3853723a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service832df111-ea07-46c5-88f6-2aca3853723a]") }
end
