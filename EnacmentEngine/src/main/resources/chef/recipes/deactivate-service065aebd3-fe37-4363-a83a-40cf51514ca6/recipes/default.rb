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

ruby_block "disable-service065aebd3-fe37-4363-a83a-40cf51514ca6" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service065aebd3-fe37-4363-a83a-40cf51514ca6::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['065aebd3-fe37-4363-a83a-40cf51514ca6']['InstallationDir']}/service-065aebd3-fe37-4363-a83a-40cf51514ca6.jar]", :immediately
end

ruby_block "remove-service065aebd3-fe37-4363-a83a-40cf51514ca6" do
  block do
  	node.run_list.remove("recipe[service065aebd3-fe37-4363-a83a-40cf51514ca6::jar]")
  end
  only_if { node.run_list.include?("recipe[service065aebd3-fe37-4363-a83a-40cf51514ca6::jar]") }
end

ruby_block "remove-deactivate-service065aebd3-fe37-4363-a83a-40cf51514ca6" do
  block do
    node.run_list.remove("recipe[deactivate-service065aebd3-fe37-4363-a83a-40cf51514ca6]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service065aebd3-fe37-4363-a83a-40cf51514ca6]") }
end
