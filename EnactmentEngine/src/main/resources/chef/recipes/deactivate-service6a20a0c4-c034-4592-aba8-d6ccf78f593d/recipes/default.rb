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

#ruby_block "disable-service6a20a0c4-c034-4592-aba8-d6ccf78f593d" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service6a20a0c4-c034-4592-aba8-d6ccf78f593d::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['6a20a0c4-c034-4592-aba8-d6ccf78f593d']['InstallationDir']}/service-6a20a0c4-c034-4592-aba8-d6ccf78f593d.jar]", :immediately
#end

ruby_block "remove-service6a20a0c4-c034-4592-aba8-d6ccf78f593d" do
  block do
  	node.run_list.remove("recipe[service6a20a0c4-c034-4592-aba8-d6ccf78f593d::jar]")
  end
  only_if { node.run_list.include?("recipe[service6a20a0c4-c034-4592-aba8-d6ccf78f593d::jar]") }
end

ruby_block "remove-deactivate-service6a20a0c4-c034-4592-aba8-d6ccf78f593d" do
  block do
    node.run_list.remove("recipe[deactivate-service6a20a0c4-c034-4592-aba8-d6ccf78f593d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6a20a0c4-c034-4592-aba8-d6ccf78f593d]") }
end
