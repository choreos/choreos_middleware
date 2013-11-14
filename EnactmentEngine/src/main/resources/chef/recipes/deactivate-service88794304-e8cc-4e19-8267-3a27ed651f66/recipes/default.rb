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

ruby_block "remove-service88794304-e8cc-4e19-8267-3a27ed651f66" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service88794304-e8cc-4e19-8267-3a27ed651f66::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/88794304-e8cc-4e19-8267-3a27ed651f66.war]", :immediately
end

ruby_block "remove-service88794304-e8cc-4e19-8267-3a27ed651f66" do
  block do
  	node.run_list.remove("recipe[service88794304-e8cc-4e19-8267-3a27ed651f66::war]")
  end
  only_if { node.run_list.include?("recipe[service88794304-e8cc-4e19-8267-3a27ed651f66::war]") }
end

ruby_block "remove-deactivate-service88794304-e8cc-4e19-8267-3a27ed651f66" do
  block do
    node.run_list.remove("recipe[deactivate-service88794304-e8cc-4e19-8267-3a27ed651f66]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service88794304-e8cc-4e19-8267-3a27ed651f66]") }
end
