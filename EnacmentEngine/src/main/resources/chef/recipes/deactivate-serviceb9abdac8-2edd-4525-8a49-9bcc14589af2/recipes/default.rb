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

ruby_block "remove-serviceb9abdac8-2edd-4525-8a49-9bcc14589af2" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb9abdac8-2edd-4525-8a49-9bcc14589af2::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/b9abdac8-2edd-4525-8a49-9bcc14589af2.war]", :immediately
end

ruby_block "remove-serviceb9abdac8-2edd-4525-8a49-9bcc14589af2" do
  block do
  	node.run_list.remove("recipe[serviceb9abdac8-2edd-4525-8a49-9bcc14589af2::war]")
  end
  only_if { node.run_list.include?("recipe[serviceb9abdac8-2edd-4525-8a49-9bcc14589af2::war]") }
end

ruby_block "remove-deactivate-serviceb9abdac8-2edd-4525-8a49-9bcc14589af2" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb9abdac8-2edd-4525-8a49-9bcc14589af2]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb9abdac8-2edd-4525-8a49-9bcc14589af2]") }
end
